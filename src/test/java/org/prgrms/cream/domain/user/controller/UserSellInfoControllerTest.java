package org.prgrms.cream.domain.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ActiveProfiles("local")
@Transactional
@AutoConfigureMockMvc
@TestConfig
@SpringBootTest
public class UserSellInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("전체 판매입찰중 내역 테스트")
	@Test
	void getSellingBidHistoriesTest() throws Exception {
		// given
		long userId = 2L;

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}/selling/bidding", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses").isArray()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].image").isString()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].name").isString()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].size").isString()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].expiredDate").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("조건부 판매입찰중 내역 테스트")
	@Test
	void conditionalGetSellingBidHistoriesTest() throws Exception {
		// given
		long userId = 2L;

		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("status", "입찰 중");

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}/selling/bidding", userId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .params(param)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses").isArray()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].image").isString()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].name").isString()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].size").isString()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.userSellingBidResponses[0].expiredDate").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("전체 진행중거래 내역 테스트")
	@Test
	void getPendingDealHistoryTest() throws Exception {
		// given
		long userId = 2L;

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}/selling/pending", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses").isArray()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].image").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].name").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].size").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].dealDate").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].status").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("조건부 진행중거래 내역 테스트")
	@Test
	void ConditionalGetPendingDealHistoryTest() throws Exception {
		// given
		long userId = 2L;

		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("status", "검수 중");

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}/selling/pending", userId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .params(param)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses").isArray()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].image").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].name").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].size").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].dealDate").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].status").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("전체 거래완료 내역 테스트")
	@Test
	void getFinishedDealHistoriesTest() throws Exception {
		// given
		long userId = 2L;

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}/selling/finished", userId)
												   .contentType(MediaType.APPLICATION_JSON)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses").isArray()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].image").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].name").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].size").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].dealDate").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].status").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("조건부 거래완료 내역 테스트")
	@Test
	void ConditionalGetFinishedDealHistoriesTest() throws Exception {
		// given
		long userId = 2L;

		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("status", "취소완료");

		// when
		ResultActions result = mockMvc.perform(get("/users/{id}/selling/finished", userId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .params(param)
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses").isArray()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].image").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].name").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].size").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].dealDate").isString()
			)
			.andExpect(
				jsonPath("$.data.userDealResponses[0].status").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("판매입찰 취소 테스트")
	@Test
	void cancelSellingBidTest() throws Exception {
		Long userId = 2L;
		Long bidId = 1L;

		ResultActions result = mockMvc.perform(delete("/users/{id}/selling/{id}", userId, bidId)
												   .contentType(MediaType.APPLICATION_JSON)

		);

		result
			.andExpect(
				status().isOk()
			);
	}
}
