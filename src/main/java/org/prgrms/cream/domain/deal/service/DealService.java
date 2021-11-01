package org.prgrms.cream.domain.deal.service;

import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DealService {

	private final DealRepository dealRepository;

	public DealService(
		DealRepository dealRepository
	) {
		this.dealRepository = dealRepository;
	}

	@Transactional
	public Deal createDeal(BuyingBid buyingBid, String size, User user, Product product) {
		Deal deal = Deal
			.builder()
			.buyer(buyingBid.getUser())
			.seller(user)
			.product(product)
			.size(size)
			.price(buyingBid.getSuggestPrice())
			.build();

		buyingBid.changeStatus(DealStatus.BID_COMPLETED);

		return dealRepository.save(deal);
	}

	public Deal createDeal(Deal deal) {
		return dealRepository.save(deal);
	}
}
