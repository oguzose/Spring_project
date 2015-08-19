package tr.org.lkd.lyk2015.camp.dal;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.model.Course;

@Repository
public class CourseDao extends GenericDao<Course> {

	@SuppressWarnings("unchecked")
	public List<Course> getByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", ids));

		return criteria.list();
	}

	@Override
	public List<Course> getAll() {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("active", true));
		return criteria.list();
	}
}