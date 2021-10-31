package org.prgrms.cream.domain.deal.service;

import java.time.format.DateTimeFormatter;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.domain.user.exception.InvalidArgumentException;
import org.prgrms.cream.domain.user.service.UserService;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellingService {

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
	public BidResponse registerBuyingBid(Long id, String size, BidRequest bidRequest) {
		ProductOption productOption = productService.findProductOptionByProductIdAndSize(id, size);

		if (bidRequest.price() > productOption.getSellHighestPrice())
			productOption.updateSellHighestPrice(bidRequest.price());
		else
			throw new InvalidArgumentException(ErrorCode.INVALID_INPUT);

		SellingBid bid = SellingBid
			.builder()
			.user(userService.findActiveUser(id))
			.product(productOption.getProduct())
			.size(size)
			.suggestPrice(bidRequest.price())
			.status("입찰중")
			.build();

		String expiredDate = bid
			.getCreatedDate()
			.plusDays(bidRequest.deadline())
			.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		sellingRepository.save(bid);

		return new BidResponse(bid.getSuggestPrice(), bid.getDeadline(), expiredDate);
	}
}
