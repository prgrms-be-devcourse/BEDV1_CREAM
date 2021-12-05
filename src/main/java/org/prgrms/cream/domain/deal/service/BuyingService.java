package org.prgrms.cream.domain.deal.service;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.BuyingHistoryResponse;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.BuyingRepository;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyingService {

	private static final int FIRST_BID = 0;
	private static final int SECOND_BID = 1;
	private static final int TWO_BIDS = 2;
	private static final int VALUE_ZERO = 0;

	private final BuyingRepository buyingRepository;
	private final SellingRepository sellingRepository;
	private final ProductService productService;
	private final UserService userService;
	private final DealService dealService;

	public BuyingService(
		BuyingRepository buyingRepository,
		SellingRepository sellingRepository,
		ProductService productService,
		UserService userService,
		DealService dealService
	) {
		this.buyingRepository = buyingRepository;
		this.sellingRepository = sellingRepository;
		this.productService = productService;
		this.userService = userService;
		this.dealService = dealService;
	}

	@Transactional
	public BidResponse registerBuyingBid(Long id, String size, BidRequest bidRequest) {
		User user = userService.findActiveUser(bidRequest.userId());
		ProductOption productOption = productService.findProductOptionByProductIdAndSize(id, size);

		Optional<BuyingBid> existingBid = buyingRepository
			.findByProductAndSizeAndStatusAndUser(
				productOption.getProduct(),
				size,
				DealStatus.BIDDING.getStatus(),
				user
			);
		if (existingBid.isPresent()) {
			return updateExistingBuyingBid(existingBid.get(), size, bidRequest, productOption);
		}

		updateHighestPrice(bidRequest.price(), productOption);
		BuyingBid newBuyingBid = buyingRepository.save(
			BuyingBid
				.builder()
				.user(user)
				.product(productOption.getProduct())
				.size(size)
				.suggestPrice(bidRequest.price())
				.deadline(bidRequest.deadline())
				.build()
		);

		return newBuyingBid.toBidResponse();
	}

	@Transactional
	public DealResponse straightBuyProduct(Long productId, String size, BuyRequest buyRequest) {
		Product product = productService.findActiveProduct(productId);
		List<SellingBid> sellingBids = sellingRepository
			.findFirst2ByProductAndSizeAndStatusOrderBySuggestPriceAscCreatedDateAsc(
				product,
				size,
				DealStatus.BIDDING.getStatus()
			);

		if (sellingBids.isEmpty()) {
			throw new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE);
		}

		ProductOption productOption = productService
			.findProductOptionByProductIdAndSize(
				productId,
				size
			);
		SellingBid topSellingBid = sellingBids.get(FIRST_BID);
		topSellingBid.changeStatus(DealStatus.BID_COMPLETED);
		if (sellingBids.size() < TWO_BIDS) {
			productOption.updateSellBidPrice(VALUE_ZERO);
		} else if (sellingBids.size() == TWO_BIDS) {
			productOption.updateSellBidPrice(
				sellingBids
					.get(SECOND_BID)
					.getSuggestPrice()
			);
		}

		return dealService
			.createDeal(
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

	@Transactional
	public void cancelBuyingBid(Long userId, Long bidId) {
		BuyingBid buyingBid = buyingRepository
			.findByIdAndUserAndStatus(
				bidId,
				userService.findActiveUser(userId),
				DealStatus.BIDDING.getStatus()
			)
			.orElseThrow(() -> new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE));
		buyingBid.cancel();

		ProductOption productOption = productService.findProductOptionByProductIdAndSize(
			buyingBid
				.getProduct()
				.getId(),
			buyingBid.getSize()
		);
		Optional<BuyingBid> topPriceBid = buyingRepository
			.findFirstByProductAndSizeAndStatusOrderBySuggestPriceDesc(
				buyingBid.getProduct(),
				buyingBid.getSize(),
				DealStatus.BIDDING.getStatus()
			);
		productOption.updateBuyBidPrice(
			topPriceBid.isEmpty() ? VALUE_ZERO : topPriceBid
				.get()
				.getSuggestPrice()
		);
	}

	@Transactional(readOnly = true)
	public BuyingHistoryResponse getBiddingHistoryByStatus(Long userId, String status) {
		return new BuyingHistoryResponse(
			buyingRepository
				.findAllByUserAndStatus(
					userService.findActiveUser(userId),
					status
				)
				.stream()
				.map(BuyingBid::toResponse)
				.toList());
	}

	@Transactional(readOnly = true)
	public BuyingHistoryResponse getAllBiddingHistory(Long userId) {
		return new BuyingHistoryResponse(
			buyingRepository
				.findAllByUser(userService.findActiveUser(userId))
				.stream()
				.map(BuyingBid::toResponse)
				.toList()
		);
	}

	private BidResponse updateExistingBuyingBid(
		BuyingBid buyingBid,
		String size,
		BidRequest bidRequest,
		ProductOption productOption
	) {
		buyingBid.update(bidRequest.price(), bidRequest.deadline());
		buyingRepository
			.findFirstByProductAndSizeAndStatusOrderBySuggestPriceDesc(
				buyingBid.getProduct(),
				size,
				DealStatus.BIDDING.getStatus()
			)
			.ifPresent(
				topPriceBid -> productOption.updateBuyBidPrice(
					topPriceBid.getSuggestPrice())
			);

		return buyingBid.toBidResponse();
	}

	private void updateHighestPrice(int price, ProductOption productOption) {
		if (productOption.getHighestPrice() < price) {
			productOption.updateBuyBidPrice(price);
		}
	}
}
