package org.prgrms.cream.domain.user.controller;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.config.TestConfig;
import org.prgrms.cream.domain.user.dto.UserSignUpTestRequest;
import org.prgrms.cream.domain.user.dto.UserUpdateTestRequest;
import org.prgrms.cream.domain.user.exception.DuplicateUserException;
import org.prgrms.cream.domain.user.exception.InvalidArgumentException;
import org.prgrms.cream.domain.user.exception.NotFoundUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("local")
@Transactional
@AutoConfigureMockMvc
@TestConfig
@SpringBootTest
class UserControllerTest {

	private static UserSignUpTestRequest userSignUpRequest;
	private static UserUpdateTestRequest userUpdateTestRequest;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("회원생성_테스트")
	@Test
	void createUserTest() throws Exception {
		// given
		String nickname = "nickName1";
		String email = "email1@email.com";
		String phone = "01012341234";
		String size = "265";
		String address = "Seoul";

		userSignUpRequest = new UserSignUpTestRequest(
			nickname,
			email,
			phone,
			size,
			address
		);

		// when
		ResultActions result = mockMvc.perform(post("/users")
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(objectMapper.writeValueAsString(
													   userSignUpRequest)
												   )
		);
		// then
		result
			.andExpect(
				status().isOk()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원생성_중복예외_테스트")
	@Test
	void saveUserMethodDuplicateExceptionTest() throws Exception {
		String nickname = "nickName1";
		String email = "email2@email.com";
		String phone = "01012341234";
		String size = "265";
		String address = "Seoul";

		userSignUpRequest = new UserSignUpTestRequest(
			nickname,
			email,
			phone,
			size,
			address
		);

		ResultActions result = mockMvc.perform(post("/users")
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(
													   objectMapper.writeValueAsString(
														   userSignUpRequest)
												   )
		);

		// then
		result
			.andExpect(
				status().is4xxClientError()
			)
			.andExpect(res -> assertTrue(res
											 .getResolvedException()
											 .getClass()
											 .isAssignableFrom(DuplicateUserException.class)
					   )
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원생성_예외_테스트")
	@Test
	void saveUserMethodValidExceptionTest() throws Exception {
		String nickname = "nickName1";
		String email = "email1";
		String phone = "01012341234";
		String size = "265";
		String address = "Seoul";

		userSignUpRequest = new UserSignUpTestRequest(
			nickname,
			email,
			phone,
			size,
			address
		);

		ResultActions result = mockMvc.perform(post("/users")
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(objectMapper.writeValueAsString(
													   userSignUpRequest)
												   )
		);

		// then
		result
			.andExpect(
				status().is5xxServerError()
			)
			.andExpect(
				res -> assertTrue(
					res
						.getResolvedException()
						.getClass()
						.isAssignableFrom(ConstraintViolationException.class)
				)
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원정보수정_테스트")
	@Test
	void updateUserTest() throws Exception {
		Long userId = 2L;
		// given
		String option = "nickname";
		String value = "updatedNickname";

		userUpdateTestRequest = new UserUpdateTestRequest(
			option,
			value
		);

		// when
		ResultActions result = mockMvc.perform(patch("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(objectMapper.writeValueAsString(
													   userUpdateTestRequest)
												   )
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data")
					.value(userId)
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원정보수정_조회예외_테스트")
	@Test
	void updateUserMethodNotFoundUserExceptionTest() throws Exception {
		Long userId = 1L;
		// given
		String option = "nickname";
		String value = "updatedNickname";

		userUpdateTestRequest = new UserUpdateTestRequest(
			option,
			value
		);

		// when
		ResultActions result = mockMvc.perform(patch("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(objectMapper.writeValueAsString(
													   userUpdateTestRequest)
												   )
		);

		// then
		result
			.andExpect(
				status().is4xxClientError()
			)
			.andExpect(
				res -> assertTrue(res
									  .getResolvedException()
									  .getClass()
									  .isAssignableFrom(NotFoundUserException.class)
				)
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원정보수정_옵션에러_테스트")
	@Test
	void updateUserMethodInvalidOptionExceptionTest() throws Exception {
		Long userId = 2L;
		// given
		String option = "Invalid Option";
		String value = "updatedNickname";

		userUpdateTestRequest = new UserUpdateTestRequest(
			option,
			value
		);

		// when
		ResultActions result = mockMvc.perform(patch("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .content(objectMapper.writeValueAsString(
													   userUpdateTestRequest)
												   )
		);

		// then
		result
			.andExpect(
				status().is4xxClientError()
			)
			.andExpect(
				res -> assertTrue(res
									  .getResolvedException()
									  .getClass()
									  .isAssignableFrom(InvalidArgumentException.class)
				)
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원조회_테스트")
	@Test
	void findUserTest() throws Exception {
		// given
		Long userId = 2L;

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.id")
					.isNumber()
			)
			.andExpect(
				jsonPath("$.data.nickname")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.email")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.phone")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.address")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.size")
					.isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원조회_조회예외_테스트")
	@Test
	void findUserMethodNotFoundExceptionTest() throws Exception {
		// given
		Long userId = 1L;

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().is4xxClientError()
			)
			.andExpect(
				res -> assertTrue(res
									  .getResolvedException()
									  .getClass()
									  .isAssignableFrom(NotFoundUserException.class)
				)
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원탈퇴_테스트")
	@Test
	void deleteUserTest() throws Exception {
		// given
		Long userId = 2L;

		// when
		ResultActions result = mockMvc.perform(delete("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data")
					.value(userId)
			)
			.andDo(
				print()
			);
	}

	@DisplayName("회원탈퇴_조회예외_테스트")
	@Test
	void deleteUserMethodNotFoundExceptionTest() throws Exception {
		// given
		Long userId = 1L;

		// when
		ResultActions result = mockMvc.perform(delete("/users/{id}", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().is4xxClientError()
			)
			.andExpect(
				res -> assertTrue(res
									  .getResolvedException()
									  .getClass()
									  .isAssignableFrom(NotFoundUserException.class)
				)
			)
			.andDo(
				print()
			);
	}
}
