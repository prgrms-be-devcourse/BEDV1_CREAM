package org.prgrms.cream.domain.user.controller;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.dto.BuyingBidResponse;
import org.prgrms.cream.domain.deal.service.BuyingService;
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

	public UserShoppingInfoController(BuyingService buyingService) {
		this.buyingService = buyingService;
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
	public ResponseEntity<ApiResponse<List<BuyingBidResponse>>> getBiddingHistories(
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
}
