package org.prgrms.cream.domain.user.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.prgrms.cream.domain.user.dto.UserResponse;
import org.prgrms.cream.domain.user.dto.UserSignUpRequest;
import org.prgrms.cream.domain.user.dto.UserUpdateRequest;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@ApiOperation(value = "회원가입", notes = "회원가입정보(dto)를 전달받아 회원가입을 합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Long>> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		return ResponseEntity.ok(ApiResponse.of(userService.saveUser(userSignUpRequest)));
	}

	@ApiOperation(value = "회원정보 수정", notes = "회원Id와 수정정보(dto)를 이용 이용하여 회원정보 각각을 수정합니다.")
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Long>> updateUser(
		@PathVariable Long id,
		@RequestBody UserUpdateRequest userUpdateRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(userService.updateUser(id, userUpdateRequest)));
	}

	@ApiOperation(value = "회원조회", notes = "회원Id를 이용하여 회원을 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> findUser(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.of(userService.findUser(id)));
	}

	@ApiOperation(value = "회원삭제", notes = "회원Id를 이용하여 회원을 삭제/탈퇴합니다.")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Long>> deleteUser(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.of(userService.deleteUser(id)));
	}
}
