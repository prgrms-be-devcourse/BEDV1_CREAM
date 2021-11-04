package org.prgrms.cream.domain.deal.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.service.SellingService;
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
@RequestMapping("/selling")
public class SellingController {

	private final SellingService sellingService;

	public SellingController(SellingService sellingService) {
		this.sellingService = sellingService;
	}

	@ApiOperation(value = "판매입찰등록 및 수정", notes = "상품Id와 신발사이즈,입찰정보(dto)를 통해 입찰을 등록 혹은 갱신합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BidResponse>> registerSellingBid(
		@PathVariable Long id,
		@RequestParam String size,
		@Valid @RequestBody BidRequest bidRequest
	) {
		if (sellingService.existsSameBid(
			id,
			size,
			bidRequest.userId(),
			DealStatus.BIDDING.getStatus())
		) {
			return ResponseEntity.ok(ApiResponse.of(
				sellingService.updateSellingBid(id, size, bidRequest)));
		}
		return ResponseEntity.ok(ApiResponse.of(
			sellingService.registerSellingBid(id, size, bidRequest)));
	}

	@ApiOperation(value = "즉시판매", notes = "상품Id와 신발사이즈,입찰정보(dto)를 통해 입찰을 등록 혹은 갱신합니다.")
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
