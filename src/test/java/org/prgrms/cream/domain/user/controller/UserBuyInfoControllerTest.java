package org.prgrms.cream.domain.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
@Transactional
class UserBuyInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("구매 입찰 내역 조회 - 전체 테스트")
	@Test
	void getTotalBiddingHistoryTest() throws Exception {
		// given
		Long userId = 6L;

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/bidding", userId)
					.contentType(MediaType.APPLICATION_JSON)
			);

		// then
		int expectedLength = 2;
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.buyingBidResponses")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.buyingBidResponses.length()")
					.value(expectedLength)
			);
	}

	@DisplayName("구매 입찰 내역 조회 - 특정 상태(조건) 테스트")
	@Test
	void getBiddingHistoryByStatusTest() throws Exception {
		// given
		Long userId = 6L;
		String status = "기한만료";

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/bidding", userId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("status", status)
			);

		// then
		int expectedLength = 0;
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.buyingBidResponses")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.buyingBidResponses.length()")
					.value(expectedLength)
			);
	}

	@DisplayName("진행 중 내역 조회 - 전체 테스트")
	@Test
	void getTotalPendingHistoryTest() throws Exception {
		// given
		Long userId = 6L;

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/pending", userId)
					.contentType(MediaType.APPLICATION_JSON)
			);

		// then
		int expectedLength = 2;
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.length()")
					.value(expectedLength)
			);
	}

	@DisplayName("진행 중 내역 조회 - 특정 상태(조건) 테스트")
	@Test
	void getPendingHistoryByStatusTest() throws Exception {
		// given
		Long userId = 6L;
		String status = "검수합격";

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/pending", userId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("status", status)
			);

		// then
		int expectedLength = 1;
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.length()")
					.value(expectedLength)
			);
	}

	@DisplayName("종료 내역 조회 - 전체 테스트")
	@Test
	void getTotalFinishedHistoryTest() throws Exception {
		// given
		Long userId = 6L;

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/finished", userId)
					.contentType(MediaType.APPLICATION_JSON)
			);

		// then
		int expectedLength = 2;
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.length()")
					.value(expectedLength)
			);
	}

	@DisplayName("종료 중 내역 조회 - 특정 상태(조건) 테스트")
	@Test
	void getFinishedHistoryByStatusTest() throws Exception {
		// given
		Long userId = 6L;
		String status = "배송완료";

		// when
		ResultActions result = mockMvc
			.perform(
				get("/users/{userId}/buying/finished", userId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("status", status)
			);

		// then
		int expectedLength = 1;
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data")
					.isArray()
			)
			.andExpect(
				jsonPath("$.data.length()")
					.value(expectedLength)
			);
	}
}
