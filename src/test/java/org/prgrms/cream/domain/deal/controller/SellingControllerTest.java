package org.prgrms.cream.domain.deal.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.config.TestConfig;
import org.prgrms.cream.domain.deal.dto.BidTestRequest;
import org.prgrms.cream.domain.deal.dto.BuyTestRequest;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
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
public class SellingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("판매입찰 등록 테스트")
	@Test
	void registerSellingBidTest() throws Exception {
		// given
		int price = 50000;
		int deadline = 5;
		long userId = 2L;
		Long productId = 2L;

		BidTestRequest bidTestRequest = new BidTestRequest(
			price,
			deadline,
			userId
		);
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("size", "240");

		// when
		ResultActions result = mockMvc.perform(put("/selling/{id}", productId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .params(param)
												   .content(objectMapper.writeValueAsString(
																bidTestRequest
															)
												   )
		);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.deadline").isNumber()
			)
			.andExpect(
				jsonPath("$.data.expiredDate").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("즉시판매 테스트")
	@Test
	void StraightSellProductTest() throws Exception {
		// given
		Long userId = 2L;
		Long productId = 2L;
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("size", "240");
		BuyTestRequest buyTestRequest = new BuyTestRequest(
			userId
		);

		// when
		ResultActions result = mockMvc.perform(post("/selling/{id}", productId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .params(param)
												   .content(objectMapper.writeValueAsString(
																buyTestRequest
															)
												   )
		);

		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.dealId").isNumber()
			)
			.andExpect(
				jsonPath("$.data.productName").isString()
			)
			.andExpect(
				jsonPath("$.data.size").isString()
			)
			.andExpect(
				jsonPath("$.data.price").isNumber()
			)
			.andExpect(
				jsonPath("$.data.createdDate").isString()
			)
			.andDo(
				print()
			);
	}

	@DisplayName("즉시판매간 입찰을 찾지못했을 때의 예외테스트")
	@Test
	void NotFoundBidExceptionTest() throws Exception {
		Long userId = 2L;
		Long productId = 16L;
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("size", "245");
		BuyTestRequest buyTestRequest = new BuyTestRequest(
			userId
		);

		// when
		ResultActions result = mockMvc.perform(post("/selling/{id}", productId)
												   .contentType(MediaType.APPLICATION_JSON)
												   .params(param)
												   .content(objectMapper.writeValueAsString(
																buyTestRequest
															)
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
									  .isAssignableFrom(NotFoundBidException.class)
				)
			)
			.andDo(
				print()
			);
	}
}




