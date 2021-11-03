package org.prgrms.cream.domain.user.controller;


import org.prgrms.cream.domain.deal.service.BuyingService;
import org.prgrms.cream.domain.deal.service.SellingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}")
public class UserShoppingInfoController {

	private final SellingService sellingService;
	private final BuyingService buyingService;

	public UserShoppingInfoController(
		SellingService sellingService,
		BuyingService buyingService
	) {
		this.sellingService = sellingService;
		this.buyingService = buyingService;
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
}
