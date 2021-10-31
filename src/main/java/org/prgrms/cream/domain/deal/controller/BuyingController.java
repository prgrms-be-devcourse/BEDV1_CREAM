package org.prgrms.cream.domain.deal.controller;

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

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BidResponse>> registerBuyingBid(
		@PathVariable Long id,
		@RequestParam String size,
		@Valid @RequestBody BidRequest bidRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(
			buyingService.registerBuyingBid(id, size, bidRequest)));
	}

	@PostMapping("/{productId}")
	public ResponseEntity<ApiResponse<DealResponse>> straightBuyProduct(
		@PathVariable Long productId,
		@RequestParam String size,
		@RequestBody BuyRequest buyRequest
	) {
		return ResponseEntity.ok(
			ApiResponse.of(buyingService.straightBuyProduct(productId, size, buyRequest)));
	}
}
