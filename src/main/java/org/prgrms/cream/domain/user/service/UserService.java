package org.prgrms.cream.domain.user.service;

import java.util.NoSuchElementException;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.dto.UserResponse;
import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.dto.UserUpdateRequest;
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

	@Transactional
	public Long updateUser(Long id, UserUpdateRequest userUpdateRequest) {
		User user = checkActiveUser(id);
		user.updateUser(userUpdateRequest);

		return user.getId();
	}

	@Transactional(readOnly = true)
	public UserResponse findUser(Long id) {
		return new UserResponse(checkActiveUser(id));
	}

	private User checkActiveUser(Long id) {
		return userRepository
			.findByIdAndIsDeleted(id, false)
			.orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
	}

	private void validateDuplicateUser(UserSignUpRequest userSignUpRequest) {
		if (userRepository.existsUserByEmail(
			userSignUpRequest.getEmail()
		)) {
			throw new DuplicateUserException("이미 존재하는 회원입니다.");
		}
	}
}
