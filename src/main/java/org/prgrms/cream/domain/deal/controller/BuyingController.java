package org.prgrms.cream.domain.deal.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.deal.service.BuyingService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buying")
public class BuyingController {

	private final BuyingService buyingService;

	public BuyingController(BuyingService buyingService) {
		this.buyingService = buyingService;
	}

	@ApiOperation(
		value = "구매 입찰 API",
		notes = "특정 상품과 해당 상품의 사이즈를 선택하여 원하는 가격을 입력, 입찰 마감기한을 선택하여 구매 입찰합니다."
	)
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BidResponse>> registerBuyingBid(
		@PathVariable Long id,
		@RequestParam String size,
		@Valid @RequestBody BidRequest bidRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(
			buyingService.registerBuyingBid(id, size, bidRequest)));
	}

	@ApiOperation(
		value = "즉시 구매 API",
		notes = "특정 상품과 해당 상품의 사이즈를 선택하여 즉시 구매가로 즉시 구매합니다."
	)
	@PostMapping("/{productId}")
	public ResponseEntity<ApiResponse<DealResponse>> straightBuyProduct(
		@PathVariable Long productId,
		@RequestParam String size,
		@RequestBody BuyRequest buyRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(
			buyingService.straightBuyProduct(productId, size, buyRequest)));
	}
}
