package tr.org.lkd.lyk2015.camp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MockBlackListService implements BlackListService {

	@Override
	public boolean validate(Long tckn, String email, String name, String surname) {
		if (email.equals("a@gmail.com")) {
			return false;
		}
		return true;
	}

}
