package org.prgrms.cream.domain.user.controller;

import javax.validation.Valid;
import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.dto.UserUpdateRequest;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Long>> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		return ResponseEntity.ok(ApiResponse.of(userService.saveUser(userSignUpRequest)));
	}

	@PatchMapping("{id}")
	public ResponseEntity<ApiResponse<Long>> updateUser(
		@PathVariable Long id,
		@RequestBody UserUpdateRequest userUpdateRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(userService.updateUser(id, userUpdateRequest)));
	}
}
