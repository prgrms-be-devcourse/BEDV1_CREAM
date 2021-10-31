package org.prgrms.cream.domain.deal.service;

import java.time.format.DateTimeFormatter;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.dto.BidRequest;
import org.prgrms.cream.domain.deal.dto.BidResponse;
import org.prgrms.cream.domain.deal.repository.BuyingRepository;
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

	public BuyingService(
		BuyingRepository buyingRepository,
		ProductService productService,
		UserService userService
	) {
		this.buyingRepository = buyingRepository;
		this.productService = productService;
		this.userService = userService;
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
}
