package org.prgrms.cream.domain.deal.controller;

import javax.validation.Valid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.service.SellingService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selling")
public class SellingController {

	private final SellingService sellingService;

	public SellingController(SellingService sellingService) {
		this.sellingService = sellingService;
	}

	@PostMapping("/{id}")
	public ResponseEntity<ApiResponse<BidResponse>> registerSellingBid(
		@PathVariable Long id,
		@RequestParam String size,
		@Valid @RequestBody BidRequest bidRequest
	) {
		if (sellingService.existsSameBid(id, size, bidRequest.userId())) {
			return ResponseEntity.ok(ApiResponse.of(
				sellingService.updateSellingBid(id, size, bidRequest)));
		}
		return ResponseEntity.ok(ApiResponse.of(
			sellingService.registerSellingBid(id, size, bidRequest)));
	}
}
