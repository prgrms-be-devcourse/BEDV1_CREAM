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
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.global.error.ErrorCode;
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
	private final SellingRepository sellingRepository;

	public SellingService(
		ProductService productService,
		UserService userService,
		BuyingService buyingService,
		DealService dealService,
		SellingRepository sellingRepository
	) {
		this.productService = productService;
		this.userService = userService;
		this.buyingService = buyingService;
		this.dealService = dealService;
		this.sellingRepository = sellingRepository;
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

	@Transactional
	public BidResponse registerSellingBid(Long id, String size, BidRequest bidRequest) {
		ProductOption productOption = productService.findProductOptionByProductIdAndSize(id, size);

		updateLowestPrice(bidRequest, productOption);

		SellingBid sellingBid = SellingBid
			.builder()
			.product(productOption.getProduct())
			.size(size)
			.user(userService.findActiveUser(bidRequest.userId()))
			.suggestPrice(bidRequest.price())
			.deadline(bidRequest.deadline())
			.build();

		sellingRepository.save(sellingBid);

		return sellingBid.toBidResponse(bidRequest);
	}

	@Transactional
	public BidResponse updateSellingBid(Long id, String size, BidRequest bidRequest) {
		SellingBid sellingBid = findSellingBid(id, size, bidRequest.userId());
		sellingBid.updateSellingBid(bidRequest.price(), bidRequest.deadline());

		ProductOption productOption = productService.findProductOptionByProductIdAndSize(id, size);

		updateLowestPrice(bidRequest, productOption);

		return sellingBid.toBidResponse(bidRequest);
	}

	@Transactional(readOnly = true)
	public SellingBid findSellingBid(Long productId, String size, Long userId) {
		return sellingRepository
			.findByUserAndProductAndSize(
				userService.findActiveUser(userId),
				productService.findActiveProduct(productId),
				size)
			.orElseThrow(() -> new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE));
	}

	public boolean existsSameBid(Long productId, String size, Long userId) {
		return sellingRepository.existsByUserAndProductAndSize(
			userService.findActiveUser(userId),
			productService.findActiveProduct(productId),
			size);
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

  private void updateLowestPrice(BidRequest bidRequest, ProductOption productOption) {
	  if (productOption.getLowestPrice() > bidRequest.price()
		  || productOption.getLowestPrice() == ZERO) {
		  productOption.updateSellBidPrice(bidRequest.price());
	  }
  }
}
