package tr.org.lkd.lyk2015.camp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MockExamValidationService implements ExamValidationService {

	@Override
	public boolean validate(Long tckn, String email) {
		if (tckn.equals(1l)) {
			return false;
		} else if (email.equals("a@gmail.com")) {
			return false;
		}

		return true;
	}

}
