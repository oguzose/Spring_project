package tr.org.lkd.lyk2015.camp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.lkd.lyk2015.camp.dal.UserDao;
import tr.org.lkd.lyk2015.camp.model.AbstractUser;

@Transactional
@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		AbstractUser abstractUser = this.userDao.getUserByEmail(email);
		if (abstractUser == null) {
			throw new UsernameNotFoundException(
					"böyle bi kullanıcı bulunamamdı!");
		}

		return abstractUser;

	}

}