package org.prgrms.cream.domain.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.config.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
@TestConfig
@Transactional
class UserBuyInfoControllerTest {

	private static final Long USER_ID = 2L;

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("구매 입찰 내역 조회 - 전체 테스트")
	@Test
	void getTotalBiddingHistoryTest() throws Exception {
		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/bidding", USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.bidHistory")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.bidHistory.length()")
					.isNumber()
			);
	}

	@DisplayName("구매 입찰 내역 조회 - 특정 상태(조건) 테스트")
	@Test
	void getBiddingHistoryByStatusTest() throws Exception {
		// given
		String status = "기한만료";

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/bidding", USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.param("status", status)
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.bidHistory")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.bidHistory.length()")
					.isNumber()
			);
	}

	@DisplayName("진행 중 내역 조회 - 전체 테스트")
	@Test
	void getTotalPendingHistoryTest() throws Exception {
		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/pending", USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.dealHistory")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.dealHistory.length()")
					.isNumber()
			);
	}

	@DisplayName("진행 중 내역 조회 - 특정 상태(조건) 테스트")
	@Test
	void getPendingHistoryByStatusTest() throws Exception {
		// given
		String status = "검수합격";

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/pending", USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.param("status", status)
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.dealHistory")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.dealHistory.length()")
					.isNumber()
			);
	}

	@DisplayName("종료 내역 조회 - 전체 테스트")
	@Test
	void getTotalFinishedHistoryTest() throws Exception {
		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/finished", USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.dealHistory")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.dealHistory.length()")
					.isNumber()
			);
	}

	@DisplayName("종료 내역 조회 - 특정 상태(조건) 테스트")
	@Test
	void getFinishedHistoryByStatusTest() throws Exception {
		// given
		String status = "배송완료";

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/finished", USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.param("status", status)
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.dealHistory")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.dealHistory.length()")
					.isNumber()
			);
	}
}
