package org.prgrms.cream.domain.deal.service;

import java.util.ArrayList;
import java.util.List;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.dto.DealHistoryResponse;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.dto.UserDealResponse;
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
	public DealHistoryResponse getFinishedDealHistory(
		Long id
	) {
		List<UserDealResponse> userDealResponses = new ArrayList<>();
		List<Deal> deals = dealRepository.findAllBySellerAndIsFinishedTrue(
			userService.findActiveUser(id)
		);

		return new DealHistoryResponse(addToDealResponse(userDealResponses, deals));
	}

	@Transactional(readOnly = true)
	public DealHistoryResponse getFinishedDealHistoryByStatus(
		Long id,
		String status
	) {
		List<UserDealResponse> userDealResponses = new ArrayList<>();
		List<Deal> deals = dealRepository.findAllBySellerAndSellingStatusAndIsFinishedTrue(
			userService.findActiveUser(id),
			status
		);

		return new DealHistoryResponse(addToDealResponse(userDealResponses, deals));
	}

	@Transactional(readOnly = true)
	public DealHistoryResponse getPendingDealHistory(
		Long id
	) {
		List<UserDealResponse> userDealResponses = new ArrayList<>();
		List<Deal> deals = dealRepository.findAllBySellerAndIsFinishedFalse(
			userService.findActiveUser(id)
		);

		return new DealHistoryResponse(addToDealResponse(userDealResponses, deals));
	}

	@Transactional(readOnly = true)
	public DealHistoryResponse getPendingDealHistoryByStatus(
		Long id,
		String status
	) {
		List<UserDealResponse> userDealResponses = new ArrayList<>();
		List<Deal> deals = dealRepository.findAllBySellerAndSellingStatusAndIsFinishedFalse(
			userService.findActiveUser(id),
			status
		);

		return new DealHistoryResponse(addToDealResponse(userDealResponses, deals));
	}

	private List<UserDealResponse> addToDealResponse(
		List<UserDealResponse> userDealResponses,
		List<Deal> deals
	) {
		for (Deal deal : deals) {
			userDealResponses.add(deal.toUserDealResponse());
		}
		return userDealResponses;
	}

	public Deal createDeal(Deal deal) {
		return dealRepository.save(deal);
	}
}
