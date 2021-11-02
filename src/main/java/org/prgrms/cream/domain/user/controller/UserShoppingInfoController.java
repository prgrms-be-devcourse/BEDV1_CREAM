package org.prgrms.cream.domain.user.controller;

import org.prgrms.cream.domain.deal.service.BuyingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
