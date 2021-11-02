package org.prgrms.cream.domain.deal.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
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
class BuyingControllerTest {

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
		long userId = 2L;
		BidRequest bidRequest = new BidRequest(
			price,
			deadline,
			userId
		);
		long productId = 2;
		String size = "240";

		// when
		ResultActions result = mockMvc
			.perform(
				put("/buying/{id}", productId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("size", size)
					.content(objectMapper.writeValueAsString(bidRequest))
			);

		// then
		String expectedExpiredDate = LocalDateTime
			.now()
			.plusDays(deadline)
			.format(
				DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		result
			.andExpect(
				status().isOk()
			)
			.andExpect(
				jsonPath("$.data.deadline")
					.value(deadline)
			)
			.andExpect(
				jsonPath("$.data.expiredDate")
					.value(expectedExpiredDate)
			)
			.andExpect(
				jsonPath("$.data.price")
					.value(price)
			);
	}

	@DisplayName("즉시 구매 테스트")
	@Test
	void straightBuyProductTest() throws Exception {
		// given
		Long productId = 2L;
		String size = "240";
		Long userId = 2L;
		BuyRequest buyRequest = new BuyRequest(userId);

		// when
		ResultActions result = mockMvc
			.perform(
				post("/buying/{id}", productId)
					.contentType(MediaType.APPLICATION_JSON)
					.param("size", size)
					.content(objectMapper.writeValueAsString(buyRequest)));

		// then
		int expectedPrice = 350000;
		String expectedProductName = "(W) Nike Dunk Low Black";
		String expectedSize = "240";
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
					.value(expectedPrice)
			)
			.andExpect(
				jsonPath("$.data.productName")
					.value(expectedProductName)
			)
			.andExpect(
				jsonPath("$.data.size")
					.value(expectedSize)
			)
			.andDo(print());
	}

	@DisplayName("입찰 취소 테스트")
	@Test
	void cancelBuyingBidTest() throws Exception {
		// given
		Long userId = 2L;
		Long buyingBidId = 16L;

		// when
		ResultActions result = mockMvc
			.perform(
				delete("/users/{userId}/buying/{buyingBidId}", userId, buyingBidId)
					.contentType(MediaType.APPLICATION_JSON));

		// then
		result
			.andExpect(
				status().isOk()
			);

		mockMvc
			.perform(
				delete("/users/{userId}/buying/{buyingBidId}", userId, buyingBidId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(
				status().isBadRequest()
			);
	}

}
