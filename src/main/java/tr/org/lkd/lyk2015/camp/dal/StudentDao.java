package tr.org.lkd.lyk2015.camp.dal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.model.Student;

@Repository
public class StudentDao extends GenericDao<Student> {

	public Student getByTckn(Long tckn) {
		Criteria c = this.createCriteria();

		c.add(Restrictions.eq("tckn", tckn));

		return (Student) c.uniqueResult();
	}

}