package org.prgrms.cream.domain.deal.service;

import java.util.List;
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
	public List<SellingBid> findSellingBidsOfLowestPrice(
		Product product,
		String size,
		DealStatus dealStatus
	) {
		List<SellingBid> sellingBids = sellingRepository
			.findFirst2ByProductAndSizeAndStatusOrderBySuggestPriceAscCreatedDateAsc(
				product,
				size,
				dealStatus.getStatus()
			);

		if (sellingBids.isEmpty()) {
			throw new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE);
		}

		return sellingBids;
	}
}
