package org.prgrms.cream.domain.deal.service;

import java.time.format.DateTimeFormatter;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.BuyingRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyingService {

	private final BuyingRepository buyingRepository;
	private final ProductService productService;
	private final UserService userService;
	private final SellingService sellingService;
	private final DealService dealService;

	public BuyingService(
		BuyingRepository buyingRepository,
		ProductService productService,
		UserService userService,
		SellingService sellingService,
		DealService dealService
	) {
		this.buyingRepository = buyingRepository;
		this.productService = productService;
		this.userService = userService;
		this.sellingService = sellingService;
		this.dealService = dealService;
	}

	@Transactional
	public BidResponse registerBuyingBid(Long id, String size, BidRequest bidRequest) {
		ProductOption productOption = productService.findProductOptionByProductIdAndSize(id, size);

		if (productOption.getHighestPrice() < bidRequest.price()) {
			productOption.updateBuyBidPrice(bidRequest.price());
		}

		User user = userService.findActiveUser(bidRequest.userId());
		BuyingBid buyingBid = buyingRepository.save(
			BuyingBid
				.builder()
				.user(user)
				.product(productOption.getProduct())
				.size(size)
				.suggestPrice(bidRequest.price())
				.deadline(bidRequest.deadline())
				.build()
		);

		String expiredDate = buyingBid
			.getCreatedDate()
			.plusDays(buyingBid.getDeadline())
			.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		return new BidResponse(buyingBid.getSuggestPrice(), buyingBid.getDeadline(), expiredDate);
	}

	@Transactional
	public DealResponse straightBuyProduct(Long productId, String size, BuyRequest buyRequest) {
		Product product = productService.findActiveProduct(productId);
		SellingBid topSellingBid = sellingService
			.findSellingBidOfHighestPrice(product, size, DealStatus.BIDDING);
		topSellingBid.changeStatus(DealStatus.BID_COMPLETED);

		SellingBid secondSellingBid = sellingService
			.findSellingBidOfHighestPrice(product, size, DealStatus.BIDDING);
		productService
			.findProductOptionByProductIdAndSize(
				productId,
				size
			)
			.updateBuyBidPrice(secondSellingBid.getSuggestPrice());

		return dealService
			.makeDeal(
				Deal
					.builder()
					.buyer(userService.findActiveUser(buyRequest.userId()))
					.seller(topSellingBid.getUser())
					.product(topSellingBid.getProduct())
					.size(size)
					.price(topSellingBid.getSuggestPrice())
					.build()
			)
			.toResponse();
	}
}
