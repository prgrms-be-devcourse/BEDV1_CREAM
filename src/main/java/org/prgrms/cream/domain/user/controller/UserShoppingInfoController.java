package org.prgrms.cream.domain.user.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.dto.BuyingHistoryResponse;
import org.prgrms.cream.domain.deal.dto.SellingHistoryResponse;
import org.prgrms.cream.domain.deal.service.BuyingService;
import org.prgrms.cream.domain.deal.service.DealService;
import org.prgrms.cream.domain.deal.service.SellingService;
import org.prgrms.cream.domain.user.dto.UserBuyingDealHistoryResponse;
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

	@ApiOperation(value = "판매입찰 취소", notes = "회원Id와 입찰Id를 이용해서 판매입찰을 취소합니다.")
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

	@ApiOperation(value = "판매입찰 내역 조회", notes = "회원Id와 상태를 이용해 판매입찰내역을 조회합니다.")
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
	public ResponseEntity<ApiResponse<UserBuyingDealHistoryResponse>> getPendingDealHistory(
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

	@ApiOperation(value = "판매진행중 내역 조회", notes = "회원Id와 상태를 이용해 판매진행중 내역을 조회합니다.")
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
	public ResponseEntity<ApiResponse<UserBuyingDealHistoryResponse>> getFinishedDealHistory(
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

	@ApiOperation(value = "판매완료 내역 조회", notes = "회원Id와 상태를 이용해 판매완료 내역을 조회합니다.")
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
