package tr.org.lkd.lyk2015.camp.dal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.model.AbstractUser;

@Repository
public class UserDao extends GenericDao<AbstractUser> {

	public AbstractUser getUserByEmail(String email) {
		Criteria c = this.createCriteria();
		c.add(Restrictions.eq("email", email));// email var mı yok mu
		return (AbstractUser) c.uniqueResult();
	}

}