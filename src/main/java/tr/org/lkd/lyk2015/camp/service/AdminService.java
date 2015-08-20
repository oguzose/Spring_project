package tr.org.lkd.lyk2015.camp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.lkd.lyk2015.camp.dal.AdminDao;
import tr.org.lkd.lyk2015.camp.model.Admin;

@Service
@Transactional
public class AdminService extends GenericService<Admin> {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Long create(Admin t) {
		t.setPassword(this.passwordEncoder.encode(t.getPassword())); // şifreleme
		return super.create(t);
	}

}