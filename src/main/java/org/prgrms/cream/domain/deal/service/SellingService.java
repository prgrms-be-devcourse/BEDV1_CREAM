package org.prgrms.cream.domain.deal.service;

import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.springframework.stereotype.Service;

@Service
public class SellingService {

	private final SellingRepository sellingRepository;

	public SellingService(SellingRepository sellingRepository) {
		this.sellingRepository = sellingRepository;
	}

	public void registerSellingBid(){

	}
}
