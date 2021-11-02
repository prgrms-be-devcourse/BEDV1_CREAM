package org.prgrms.cream.domain.deal.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.deal.dto.BidRequest;
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
}
