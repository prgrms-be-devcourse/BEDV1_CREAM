package org.prgrms.cream.domain.deal.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.config.TestConfig;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
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
class BuyingControllerTest {

	private static final long USER_ID = 2L;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("구매 입찰 등록 테스트")
	@Test
	void registerBuyingBidTest() throws Exception {
		// given
		int price = 320000;
		int deadline = 10;
		BidRequest bidRequest = new BidRequest(
			price,
			deadline,
			USER_ID
		);
		long productId = 5L;
		String size = "240";

		// when
		ResultActions result = mockMvc
			.perform(
				put("/buying/{id}", productId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("size", size)
					.content(objectMapper.writeValueAsString(bidRequest))
			);

		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.deadline")
					.isNumber()
			)
			.andExpect(
				jsonPath("$.data.expiredDate")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.price")
					.isNumber()
			);
	}

	@DisplayName("즉시 구매 테스트")
	@Test
	void straightBuyProductTest() throws Exception {
		// given
		Long productId = 2L;
		String size = "240";
		BuyRequest buyRequest = new BuyRequest(USER_ID);

		// when
		ResultActions result = mockMvc
			.perform(
				post("/buying/{id}", productId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("size", size)
					.content(objectMapper.writeValueAsString(buyRequest))
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.dealId")
					.isNumber()
			)
			.andExpect(
				jsonPath("$.data.price")
					.isNumber()
			)
			.andExpect(
				jsonPath("$.data.productName")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.size")
					.isString()
			)
			.andDo(print());
	}

	@DisplayName("입찰 취소 테스트")
	@Test
	void cancelBuyingBidTest() throws Exception {
		// given
		Long buyingBidId = 16L;

		// when
		ResultActions result = mockMvc
			.perform(
				delete("/users/{userId}/buying/{buyingBidId}", USER_ID, buyingBidId)
					.contentType(MediaType.APPLICATION_JSON));

		// then
		result
			.andExpect(
				status().isOk()
			);

		mockMvc
			.perform(
				delete("/users/{userId}/buying/{buyingBidId}", USER_ID, buyingBidId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(
				status().isBadRequest()
			)
			.andExpect(
				res ->
					assertThat(
						Objects
							.requireNonNull(res.getResolvedException())
							.getClass()
							.isAssignableFrom(NotFoundBidException.class)
					).isTrue()
			);
	}

	@DisplayName("구매 입찰 변경 테스트")
	@Test
	void updateBuyingBidTest() throws Exception {
		// given
		int price = 320000;
		int deadline = 10;
		BidRequest bidRequest = new BidRequest(
			price,
			deadline,
			USER_ID
		);
		Long productId = 3L;
		String size = "250";

		// when
		ResultActions result = mockMvc
			.perform(
				put("/buying/{id}", productId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("size", size)
					.content(objectMapper.writeValueAsString(bidRequest))
			);

		// then
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.deadline")
					.isNumber()
			)
			.andExpect(
				jsonPath("$.data.expiredDate")
					.isString()
			)
			.andExpect(
				jsonPath("$.data.price")
					.isNumber()
			);
	}
}
