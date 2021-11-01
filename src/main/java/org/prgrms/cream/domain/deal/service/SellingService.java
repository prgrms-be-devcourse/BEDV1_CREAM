package org.prgrms.cream.domain.deal.service;

import java.util.List;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellingService {

	private static final int FIRST_BID = 0;
	private static final int SECOND_BID = 1;
	private static final int ZERO = 0;
	private static final int ONLY_ONE_BID = 1;
	private static final int ALL_BID = 2;
	private final ProductService productService;
	private final UserService userService;
	private final BuyingService buyingService;
	private final DealService dealService;

	public SellingService(
		ProductService productService,
		UserService userService,
		BuyingService buyingService,
		DealService dealService
	) {
		this.productService = productService;
		this.userService = userService;
		this.buyingService = buyingService;
		this.dealService = dealService;
	}

	@Transactional
	public DealResponse straightSellProduct(Long productId, String size, BuyRequest buyRequest) {
		List<BuyingBid> buyingBids = buyingService.findBuyingBid(
			productId, size, DealStatus.BIDDING);

		BuyingBid firstBuyingBid = buyingBids.get(FIRST_BID);

		Deal deal = dealService.createDeal(
			firstBuyingBid,
			size,
			userService.findActiveUser(buyRequest.userId()),
			productService.findActiveProduct(productId)
		);

		ProductOption productOption = productService.findProductOptionByProductIdAndSize(
			productId, size);

		if (buyingBids.size() == ONLY_ONE_BID) {
			productOption.updateBuyBidPrice(ZERO);
		} else if (buyingBids.size() == ALL_BID) {
			BuyingBid secondBuyingBid = buyingBids.get(SECOND_BID);
			productOption.updateBuyBidPrice(secondBuyingBid.getSuggestPrice());
		}

		return deal.toResponse();
	}
}
