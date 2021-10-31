package org.prgrms.cream.domain.deal.service;

import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.springframework.stereotype.Service;

@Service
public class DealService {

	private final DealRepository dealRepository;

	public DealService(DealRepository dealRepository) {
		this.dealRepository = dealRepository;
	}

	public Deal makeDeal(Deal deal) {
		return dealRepository.save(deal);
	}
}
