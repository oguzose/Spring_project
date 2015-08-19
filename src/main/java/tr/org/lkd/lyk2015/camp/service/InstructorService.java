package tr.org.lkd.lyk2015.camp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.lkd.lyk2015.camp.dal.CourseDao;
import tr.org.lkd.lyk2015.camp.dal.InstructorDao;
import tr.org.lkd.lyk2015.camp.model.Course;
import tr.org.lkd.lyk2015.camp.model.Instructor;

@Service
@Transactional
public class InstructorService extends GenericService<Instructor> {

	@Autowired
	private InstructorDao instructorDao;

	@Autowired
	private CourseDao courseDao;

	public void create(Instructor instructor, List<Long> ids) {

		List<Course> courses = this.courseDao.getByIds(ids);
		instructor.getCourses().addAll(courses);
		/*
		 * Set<Course> setCourse = new HashSet<>(); setCourse.addAll(courses);
		 * 
		 * instructor.setCourses(setCourse);
		 */

		this.instructorDao.create(instructor);
	}

	public Instructor getInstructorWithCourses(Long id) {
		Instructor instructor = this.instructorDao.getByIdWithCourses(id);

		return instructor;
	}

}