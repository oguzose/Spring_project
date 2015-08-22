package tr.org.lkd.lyk2015.camp.service;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.lkd.lyk2015.camp.dal.ApplicationDao;
import tr.org.lkd.lyk2015.camp.dal.CourseDao;
import tr.org.lkd.lyk2015.camp.dal.StudentDao;
import tr.org.lkd.lyk2015.camp.dto.ApplicationFormDto;
import tr.org.lkd.lyk2015.camp.model.Application;
import tr.org.lkd.lyk2015.camp.model.Course;
import tr.org.lkd.lyk2015.camp.model.Student;

@Service
@Transactional
public class ApplicationService extends GenericService<Application> {

	// @Autowired
	// ApplicationDao applicationDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentService studentService;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	StudentDao studentDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String URL_BASE = "http://localhost:8080/camp/application/validate/";

	private static final String onayMesajı = "Başvurunuz doğrulanmıştır. Seçilme sonuçları için mailinize bakmayı unutmayın";

	public void create(ApplicationFormDto applicationFormDto) {

		// NESNELERİMİZ
		Application application = applicationFormDto.getApplication();
		Student student = applicationFormDto.getStudent();

		// UUID ÜRET Ve link hazırla
		String url = URL_BASE + this.generateUUID(application);

		/*
		 * String uuid = UUID.randomUUID().toString();
		 * 
		 * String password = UUID.randomUUID().toString();
		 * 
		 * password = password.substring(0, 5); url = URL_BASE + uuid;
		 * 
		 * String emailContnet = " Doğrulamak için tıklayınız" + url +
		 * "Parolaniz" + password;
		 */

		// COURSELARI ALIP SET EDELİM
		this.getCoursesByIds(application,
				applicationFormDto.getPreferredCourseIds());

		String password = UUID.randomUUID().toString();
		password = password.substring(0, 6);

		// APPLICATION OBJESİNE STUDENTİ BAĞLAYALIM
		Student studentFromDao = this.studentDao.getByTckn(applicationFormDto
				.getStudent().getTckn());

		if (studentFromDao == null) {// student yoksa
			// BOYLE YAPMAMIZIN NEDENI STUDENTI DATABASEDEN ÇEKMEMİZ GEREKİYOR
			// YOKSA OLMAZ; CASCADE EKLEMEMİZ GEREKİYOR
			// STUDENTI OLUŞTURUP SONRA APPLICATION ILE İLİŞKİLENDİREBİLİRZ
			student.setPassword(this.passwordEncoder.encode(password));

			this.studentDao.create(applicationFormDto.getStudent());
			studentFromDao = student;
			application.setOwner(studentFromDao);

		} else { // student var ise
			application.setOwner(studentFromDao);
		}

		application.setYear(Calendar.getInstance().get(Calendar.YEAR));
		url = url + "\ngiriş bilgileriniz\n" + studentFromDao.getEmail()
				+ "\nŞifreniz: " + password;

		if (this.emailService.sendConfirmation(student.getEmail(),
				"Başvuru Onayı", url)) {
			System.out.println("email gönderildi.");
		} else {
			System.out.println("email gönderilemedi");
		}
		this.applicationDao.create(application);

	}

	public String generateUUID(Application application) {
		String uuid = UUID.randomUUID().toString();
		application.setValidateId(uuid);
		return uuid;
	}

	public void getCoursesByIds(Application application, List<Long> ids) {

		List<Course> courses = this.courseDao.getByIds(ids);
		application.getPreferredCourses().clear();
		application.getPreferredCourses().addAll(courses);
	}

	public boolean validate(String validationId) {

		Application application = this.applicationDao
				.getByValidationId(validationId);// boyle bir application varmı
		if (application == null) {
			return false;
		}

		Student student = application.getOwner();
		application.setValidated(true);
		// update çağırmasam da transactional old için
		// yaptığımd eğişiklikleri direk database yazar
		this.applicationDao.update(application);
		if (this.emailService.sendConfirmation(student.getEmail(),
				"Başvuru Formu Doğrulaması", onayMesajı)) {
			System.out.println("email gönderildi.");
		} else {
			System.out.println("email gönderilemedi");
		}
		return true;

	}

	public ApplicationFormDto createApplicationFormDto(Long id) {

		Application application = this.applicationDao
				.getStudentsApplication(id);
		Student student = this.studentDao.getById(id);
		List<Long> courseIds = new ArrayList<>();
		for (Course course : application.getPreferredCourses()) {
			courseIds.add(course.getId());
		}

		// null göndermemesi için
		int emptySize = 3 - courseIds.size();
		for (int i = 0; i < emptySize; i++) {
			courseIds.add(null);
		}

		ApplicationFormDto applicationFormDto = new ApplicationFormDto();
		applicationFormDto.setApplication(application);
		applicationFormDto.setPreferredCourseIds(courseIds);
		applicationFormDto.setStudent(student);

		return applicationFormDto;

	}

	public void update(ApplicationFormDto applicationFormDto) {

		Application application = applicationFormDto.getApplication();

		this.getCoursesByIds(application,
				applicationFormDto.getPreferredCourseIds());
		Application applicationFromDb = this.applicationDao.getById(application
				.getId());
		applicationFromDb.setCorporation(application.getCorporation());
		applicationFromDb.setGithubLink(application.getGithubLink());
		applicationFromDb.setEnglishLevel(application.getEnglishLevel());
		applicationFromDb.setNeedAccomodation(application.isNeedAccomodation());
		applicationFromDb.setWorkDetails(application.getWorkDetails());
		applicationFromDb.setOfficer(application.isOfficer());
		applicationFromDb.setWorkStatus(application.getWorkStatus());

	}

	// ben kimim -- o application bu studentın mı
	public void isUserAuthorizedForForm(Student student, Application application) {
		Application applicationFromDb = this.applicationDao
				.getStudentsApplication(student.getId());

		if (applicationFromDb == null
				|| !applicationFromDb.getId().equals(application.getId())) {
			throw new AccessControlException(
					"This form is not owned by current user");
		}

	}

}