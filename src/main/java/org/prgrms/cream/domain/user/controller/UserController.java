package org.prgrms.cream.domain.user.controller;

import javax.validation.Valid;
import org.prgrms.cream.domain.user.dto.UserResponse;
import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.dto.UserUpdateRequest;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ApiResponse<Long> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		return ApiResponse.of(userService.saveUser(userSignUpRequest));
	}

	@PatchMapping("{id}")
	public ApiResponse<Long> updateUser(
		@PathVariable Long id,
		@RequestBody UserUpdateRequest userUpdateRequest
	) {
		return ApiResponse.of(userService.updateUser(id, userUpdateRequest));
	}

	@GetMapping("{id}")
	public ApiResponse<UserResponse> findUser(@PathVariable Long id){
		return ApiResponse.of(userService.findUser(id));
	}
}
