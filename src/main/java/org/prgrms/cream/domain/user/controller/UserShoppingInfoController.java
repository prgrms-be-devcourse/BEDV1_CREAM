package org.prgrms.cream.domain.user.controller;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.dto.BuyingHistoryResponse;
import org.prgrms.cream.domain.deal.dto.DealHistoryResponse;
import org.prgrms.cream.domain.deal.dto.SellingHistoryResponse;
import org.prgrms.cream.domain.deal.service.BuyingService;
import org.prgrms.cream.domain.deal.service.DealService;
import org.prgrms.cream.domain.deal.service.SellingService;
import org.prgrms.cream.domain.user.dto.UserDealHistoryResponse;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}")
public class UserShoppingInfoController {

	private final BuyingService buyingService;
	private final SellingService sellingService;
	private final DealService dealService;

	public UserShoppingInfoController(
		BuyingService buyingService,
		SellingService sellingService,
		DealService dealService
	) {
		this.buyingService = buyingService;
		this.sellingService = sellingService;
		this.dealService = dealService;
	}

	@DeleteMapping("/selling/{bidId}")
	public ResponseEntity<Void> cancelSellingBid(
		@PathVariable Long userId,
		@PathVariable Long bidId
	) {
		sellingService.cancelSellingBid(bidId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/buying/{bidId}")
	public ResponseEntity<Void> cancelBuyingBid(
		@PathVariable Long userId,
		@PathVariable Long bidId
	) {
		buyingService.cancelBuyingBid(userId, bidId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/buying/bidding")
	public ResponseEntity<ApiResponse<BuyingHistoryResponse>> getBiddingHistory(
		@PathVariable Long userId,
		@RequestParam Optional<String> status
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				status
					.map(bidStatus -> buyingService.getBiddingHistoryByStatus(userId, bidStatus))
					.orElse(buyingService.getAllBiddingHistory(userId))
			)
		);
	}

	@GetMapping("/selling/bidding")
	public ResponseEntity<ApiResponse<SellingHistoryResponse>> getSellingBidHistories(
		@PathVariable Long userId,
		@RequestParam Optional<String> status
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				status
					.map(
						biddingStatus -> sellingService.getAllSellingHistoryByStatus(
							userId,
							biddingStatus
						)
					)
					.orElse(sellingService.getAllSellingHistory(userId))
			)
		);
	}

	@GetMapping("/buying/pending")
	public ResponseEntity<ApiResponse<List<DealHistoryResponse>>> getPendingDealHistory(
		@PathVariable Long userId,
		@RequestParam Optional<String> status
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				status
					.map(dealStatus -> dealService.getPendingDealByStatus(userId, dealStatus))
					.orElse(dealService.getAllPendingDealHistory(userId))
			)
		);
	}

	@GetMapping("/selling/pending")
	public ResponseEntity<ApiResponse<UserDealHistoryResponse>> getPendingDealHistories(
		@PathVariable Long userId,
		@RequestParam Optional<String> status
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				status
					.map(
						dealStatus -> dealService.getPendingDealHistoryByStatus(
							userId,
							dealStatus
						)
					)
					.orElse(dealService.getPendingDealHistory(userId))
			)
		);
	}

	@GetMapping("/buying/finished")
	public ResponseEntity<ApiResponse<List<DealHistoryResponse>>> getFinishedDealHistory(
		@PathVariable Long userId,
		@RequestParam Optional<String> status
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				status
					.map(dealStatus -> dealService.getFinishedDealByStatus(userId, dealStatus))
					.orElse(dealService.getAllFinishedDealHistory(userId))

			)
		);
	}

	@GetMapping("/selling/finished")
	public ResponseEntity<ApiResponse<UserDealHistoryResponse>> getFinishedDealHistories(
		@PathVariable Long userId,
		@RequestParam Optional<String> status
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				status
					.map(
						dealStatus -> dealService.getFinishedDealHistoryByStatus(
							userId,
							dealStatus
						)
					)
					.orElse(dealService.getFinishedDealHistory(userId))
			)
		);
	}
}
