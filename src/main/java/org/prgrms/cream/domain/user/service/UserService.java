package org.prgrms.cream.domain.user.service;

import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.dto.UserUpdateRequest;
import org.prgrms.cream.domain.user.exception.DuplicateUserException;
import org.prgrms.cream.domain.user.exception.NotFoundUserException;
import org.prgrms.cream.domain.user.repository.UserRepository;
import org.prgrms.cream.global.error.ErrorCode;
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

	@Transactional
	public Long updateUser(Long id, UserUpdateRequest userUpdateRequest) {
		User user = findActiveUser(id);
		user.updateUser(userUpdateRequest);

		return user.getId();
	}

	private User findActiveUser(Long id) {
		return userRepository
			.findByIdAndIsDeleted(id, false)
			.orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_ERROR));
	}

	private void validateDuplicateUser(UserSignUpRequest userSignUpRequest) {
		if (userRepository.existsUserByEmail(
			userSignUpRequest.getEmail()
		)) {
			throw new DuplicateUserException(ErrorCode.CONFLICT_ERROR);
		}
	}
}
