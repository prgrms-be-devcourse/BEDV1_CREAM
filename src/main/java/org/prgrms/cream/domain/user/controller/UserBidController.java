package org.prgrms.cream.domain.user.controller;

import org.prgrms.cream.domain.deal.dto.SellingHistoryResponse;
import org.prgrms.cream.domain.user.service.UserBidService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserBidController {

	private final UserBidService userBidService;

	public UserBidController(UserBidService userBidService) {
		this.userBidService = userBidService;
	}

	@GetMapping("/users/{id}/selling")
	public ResponseEntity<ApiResponse<SellingHistoryResponse>> getSellingBidHistory(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.of(userBidService.getSellingHistory(id)));
	}
}
