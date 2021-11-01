package org.prgrms.cream.domain.deal.service;

import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellingService {

	private static final int ZERO = 0;

	private final SellingRepository sellingRepository;
	private final ProductService productService;
	private final UserService userService;

	public SellingService(
		SellingRepository sellingRepository,
		ProductService productService,
		UserService userService
	) {
		this.sellingRepository = sellingRepository;
		this.productService = productService;
		this.userService = userService;
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

	private void updateLowestPrice(BidRequest bidRequest, ProductOption productOption) {
		if (productOption.getLowestPrice() > bidRequest.price()
			|| productOption.getLowestPrice() == ZERO) {
			productOption.updateSellBidPrice(bidRequest.price());
		}
	}
}
