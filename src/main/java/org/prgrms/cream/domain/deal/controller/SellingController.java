package org.prgrms.cream.domain.deal.controller;

import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.service.SellingService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SellingController {

	private final SellingService sellingService;

	public SellingController(SellingService sellingService) {
		this.sellingService = sellingService;
	}

	@PostMapping("/selling/{id}")
	public ResponseEntity<ApiResponse<BidResponse>> createSellingBid(
		@PathVariable Long id,
		@RequestParam String size,
		@RequestBody BidRequest bidRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(
			sellingService.registerBuyingBid(id, size, bidRequest)));
	}
}
