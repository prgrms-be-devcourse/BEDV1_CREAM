package org.prgrms.cream.domain.deal.controller;

import org.prgrms.cream.domain.deal.service.SellingService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellingController {
	private final SellingService sellingService;

	public SellingController(SellingService sellingService) {
		this.sellingService = sellingService;
	}

}
