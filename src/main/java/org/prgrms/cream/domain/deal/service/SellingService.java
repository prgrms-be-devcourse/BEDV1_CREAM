package org.prgrms.cream.domain.deal.service;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.dto.BuyRequest;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.deal.dto.SellingHistoryResponse;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.BuyingRepository;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.domain.user.service.UserService;
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
	private final DealService dealService;
	private final SellingRepository sellingRepository;
	private final BuyingRepository buyingRepository;

	public SellingService(
		ProductService productService,
		UserService userService,
		DealService dealService,
		SellingRepository sellingRepository,
		BuyingRepository buyingRepository
	) {
		this.productService = productService;
		this.userService = userService;
		this.dealService = dealService;
		this.sellingRepository = sellingRepository;
		this.buyingRepository = buyingRepository;
	}

	@Transactional
	public DealResponse straightSellProduct(Long productId, String size, BuyRequest buyRequest) {
		Product product = productService.findActiveProduct(productId);
		List<BuyingBid> buyingBids = buyingRepository
			.findTop2ByProductAndSizeAndStatusOrderBySuggestPriceDescCreatedDateAsc(
				product,
				size,
				DealStatus.BIDDING.getStatus()
			);

		if (buyingBids.isEmpty()) {
			throw new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE);
		}

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

		if (existsSameBid(id, size, bidRequest.userId(), DealStatus.BIDDING.getStatus())) {
			SellingBid sellingBid = findSellingBid(id, size, bidRequest.userId());
			sellingBid.updateSellingBid(bidRequest.price(), bidRequest.deadline());
			updateLowestPrice(bidRequest, productOption);
			return sellingBid.toBidResponse(bidRequest);
		}

		SellingBid sellingBid = SellingBid
			.builder()
			.product(productOption.getProduct())
			.size(size)
			.user(userService.findActiveUser(bidRequest.userId()))
			.suggestPrice(bidRequest.price())
			.deadline(bidRequest.deadline())
			.build();

		sellingRepository.save(sellingBid);
		updateLowestPrice(bidRequest, productOption);

		return sellingBid.toBidResponse(bidRequest);
	}

	@Transactional(readOnly = true)
	public SellingBid findSellingBid(Long productId, String size, Long userId) {
		return sellingRepository
			.findByUserAndProductAndSize(
				userService.findActiveUser(userId),
				productService.findActiveProduct(productId),
				size
			)
			.orElseThrow(() -> new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE));
	}

	@Transactional(readOnly = true)
	public SellingHistoryResponse getAllSellingHistory(
		Long id
	) {
		return new SellingHistoryResponse(
			sellingRepository
				.findAllByUser(
					userService.findActiveUser(id)
				)
				.stream()
				.map(SellingBid::toSellingBidResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public SellingHistoryResponse getAllSellingHistoryByStatus(
		Long id,
		String status
	) {
		return new SellingHistoryResponse(
			sellingRepository
				.findAllByUserAndStatus(
					userService.findActiveUser(id),
					status
				)
				.stream()
				.map(SellingBid::toSellingBidResponse)
				.toList()
		);
	}

	@Transactional
	public void cancelSellingBid(Long bidId, Long userId) {
		SellingBid sellingBid = sellingRepository
			.findByIdAndUserAndStatus(
				bidId,
				userService.findActiveUser(userId),
				DealStatus.BIDDING.getStatus()
			)
			.orElseThrow(() -> new NotFoundBidException(ErrorCode.NOT_FOUND_RESOURCE));

		sellingBid.changeStatus(DealStatus.BID_CANCELLED);

		Optional<SellingBid> findSellingBid = sellingRepository
			.findTopByProductAndSizeAndStatusOrderBySuggestPriceAscCreatedDateAsc(
				sellingBid.getProduct(),
				sellingBid.getSize(),
				DealStatus.BIDDING.getStatus()
			);

		ProductOption productOption = productService
			.findProductOptionByProductIdAndSize(
				sellingBid
					.getProduct()
					.getId(),
				sellingBid
					.getSize()
			);

		productOption.updateSellBidPrice(findSellingBid.isEmpty() ? ZERO : findSellingBid
			.get()
			.getSuggestPrice()
		);
	}

	public boolean existsSameBid(Long productId, String size, Long userId, String status) {
		return sellingRepository.existsByUserAndProductAndSizeAndStatus(
			userService.findActiveUser(userId),
			productService.findActiveProduct(productId),
			size,
			status
		);
	}

	private void updateLowestPrice(BidRequest bidRequest, ProductOption productOption) {
		if (productOption.getLowestPrice() > bidRequest.price()
			|| productOption.getLowestPrice() == ZERO) {
			productOption.updateSellBidPrice(bidRequest.price());
		}
	}
}
