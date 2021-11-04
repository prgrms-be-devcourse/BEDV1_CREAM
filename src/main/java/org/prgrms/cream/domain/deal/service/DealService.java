package org.prgrms.cream.domain.deal.service;

import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.dto.UserBuyingDealHistoryResponse;
import org.prgrms.cream.domain.user.dto.UserDealHistoryResponse;
import org.prgrms.cream.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DealService {

	private final DealRepository dealRepository;
	private final UserService userService;

	public DealService(
		DealRepository dealRepository,
		UserService userService
	) {
		this.dealRepository = dealRepository;
		this.userService = userService;
	}

	@Transactional
	public Deal createDeal(BuyingBid buyingBid, String size, User user, Product product) {
		Deal deal = Deal
			.builder()
			.buyer(buyingBid.getUser())
			.seller(user)
			.product(product)
			.size(size)
			.price(buyingBid.getSuggestPrice())
			.build();

		buyingBid.changeStatus(DealStatus.BID_COMPLETED);

		return dealRepository.save(deal);
	}

	@Transactional(readOnly = true)
	public UserDealHistoryResponse getFinishedDealHistory(
		Long id
	) {
		return new UserDealHistoryResponse(
			dealRepository
				.findAllBySellerAndIsFinishedTrue(
					userService.findActiveUser(id)
				)
				.stream()
				.map(Deal::toUserDealResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public UserDealHistoryResponse getFinishedDealHistoryByStatus(
		Long id,
		String status
	) {
		return new UserDealHistoryResponse(
			dealRepository
				.findAllBySellerAndSellingStatusAndIsFinishedTrue(
					userService.findActiveUser(id),
					status
				)
				.stream()
				.map(Deal::toUserDealResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public UserDealHistoryResponse getPendingDealHistory(
		Long id
	) {
		return new UserDealHistoryResponse(
			dealRepository
				.findAllBySellerAndIsFinishedFalse(
					userService.findActiveUser(id)
				)
				.stream()
				.map(Deal::toUserDealResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public UserDealHistoryResponse getPendingDealHistoryByStatus(
		Long id,
		String status
	) {
		return new UserDealHistoryResponse(
			dealRepository
				.findAllBySellerAndSellingStatusAndIsFinishedFalse(
					userService.findActiveUser(id),
					status
				)
				.stream()
				.map(Deal::toUserDealResponse)
				.toList()
		);
	}

	public Deal createDeal(Deal deal) {
		return dealRepository.save(deal);
	}

	@Transactional(readOnly = true)
	public UserBuyingDealHistoryResponse getPendingDealByStatus(Long userId, String status) {
		return new UserBuyingDealHistoryResponse(
			dealRepository
				.findAllByBuyerAndBuyingStatusAndIsFinishedFalse(
					userService.findActiveUser(userId),
					status
				)
				.stream()
				.map(Deal::toHistoryResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public UserBuyingDealHistoryResponse getAllPendingDealHistory(Long userId) {
		return new UserBuyingDealHistoryResponse(
			dealRepository
				.findAllByBuyerAndIsFinishedFalse(
					userService.findActiveUser(userId))
				.stream()
				.map(Deal::toHistoryResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public UserBuyingDealHistoryResponse getFinishedDealByStatus(Long userId, String status) {
		return new UserBuyingDealHistoryResponse(
			dealRepository
				.findAllByBuyerAndBuyingStatusAndIsFinishedTrue(
					userService.findActiveUser(userId),
					status
				)
				.stream()
				.map(Deal::toHistoryDateResponse)
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public UserBuyingDealHistoryResponse getAllFinishedDealHistory(Long userId) {
		return new UserBuyingDealHistoryResponse(
			dealRepository
				.findAllByBuyerAndIsFinishedTrue(
					userService.findActiveUser(userId))
				.stream()
				.map(Deal::toHistoryDateResponse)
				.toList()
		);
	}
}
