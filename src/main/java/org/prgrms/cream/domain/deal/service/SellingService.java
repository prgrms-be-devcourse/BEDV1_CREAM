package org.prgrms.cream.domain.deal.service;

import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellingService {

	private final SellingRepository sellingRepository;

	public SellingService(SellingRepository sellingRepository) {
		this.sellingRepository = sellingRepository;
	}

	@Transactional(readOnly = true)
	public SellingBid findSellingBidOfHighestPrice(
		Product product,
		String size,
		DealStatus dealStatus
	) {
		return sellingRepository
			.findFirstByProductAndSizeAndStatusOrderBySuggestPriceDescCreatedDateAsc(
				product,
				size,
				dealStatus.getStatus()
			)
			.orElseThrow(() -> new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE));
	}
}
