package org.prgrms.cream.domain.product.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.cream.domain.product.dto.ProductTestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminControllerTest {

	private static final Long ID = 2L;
	private static final String BRAND = "Nike";
	private static final String ENGLISH_NAME = "Nike Dunk Low Black";
	private static final String KOREAN_NAME = "나이키 덩크 로우 블랙";
	private static final String MODEL_NUMBER = "DD1503-101";
	private static final String COLOR = "WHITE/BLACK";
	private static final int RELEASE_PRICE = 119000;
	private static final String RELEASE_DATE = "21/01/04";
	private static final List<String> SIZES = new ArrayList<>(List.of("240", "245"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("상품 등록 테스트")
	@Test
	void registerProductTest() throws Exception {
		ProductTestRequest productTestRequest = new ProductTestRequest(
			BRAND,
			ENGLISH_NAME,
			KOREAN_NAME,
			MODEL_NUMBER,
			COLOR,
			RELEASE_PRICE,
			RELEASE_DATE,
			SIZES
		);

		byte[] file = "image" .getBytes(StandardCharsets.UTF_8);
		MockMultipartFile filePart = new MockMultipartFile(
			"file", "test", null, file);
		byte[] json = objectMapper
			.writeValueAsString(productTestRequest)
			.getBytes(StandardCharsets.UTF_8);
		MockMultipartFile jsonPart = new MockMultipartFile(
			"request", "request", "application/json", json);

		this.mockMvc
			.perform(MockMvcRequestBuilders
						 .multipart("/admin/products")
						 .file(filePart)
						 .file(jsonPart))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNumber());
	}

	@DisplayName("상품 수정 테스트")
	@Test
	void modifyProductTest() throws Exception {
		ProductTestRequest productTestRequest = new ProductTestRequest(
			BRAND,
			ENGLISH_NAME,
			KOREAN_NAME,
			MODEL_NUMBER,
			COLOR,
			RELEASE_PRICE,
			RELEASE_DATE,
			SIZES
		);

		this.mockMvc
			.perform(MockMvcRequestBuilders
						 .put("/admin/products/{id}", ID)
						 .content(objectMapper.writeValueAsString(productTestRequest))
						 .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(ID));
	}

	@DisplayName("상품 삭제 테스트")
	@Test
	void removeProductTest() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders
						 .delete("/admin/products/{id}", ID))
			.andExpect(status().isNoContent());
	}
}
