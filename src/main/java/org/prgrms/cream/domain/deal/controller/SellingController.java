package org.prgrms.cream.domain.deal.controller;

import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.DealResponse;
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
	public ResponseEntity<ApiResponse<DealResponse>> straightSellProduct(
		@PathVariable Long id,
		@RequestParam String size,
		@RequestBody BuyRequest buyRequest
	) {
		return ResponseEntity.ok(
			ApiResponse.of(sellingService.straightSellProduct(id, size, buyRequest)));
	}
}
