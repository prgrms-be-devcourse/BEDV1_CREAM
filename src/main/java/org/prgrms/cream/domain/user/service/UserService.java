package org.prgrms.cream.domain.user.service;

import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.exception.DuplicateUserException;
import org.prgrms.cream.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public Long saveUser(UserSignUpRequest userSignUpRequest) {
		validateDuplicateUser(userSignUpRequest);
		return userRepository
			.save(userSignUpRequest.toEntity())
			.getId();
	}

	private void validateDuplicateUser(UserSignUpRequest userSignUpRequest) {
		if (userRepository.existsUserByEmail(
			userSignUpRequest.getEmail()
		)) {
			throw new DuplicateUserException("이미 존재하는 회원입니다.");
		}
	}
}
