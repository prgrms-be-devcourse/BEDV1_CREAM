package org.prgrms.cream.domain.user.service;

import java.util.ArrayList;
import java.util.List;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.SellingBidResponse;
import org.prgrms.cream.domain.deal.dto.SellingHistoryResponse;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.dto.UserDealResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBidService {

	private final UserService userService;
	private final SellingRepository sellingRepository;
	private final DealRepository dealRepository;

	public UserBidService(
		UserService userService,
		SellingRepository sellingRepository,
		DealRepository dealRepository
	) {
		this.userService = userService;
		this.sellingRepository = sellingRepository;
		this.dealRepository = dealRepository;
	}


	@Transactional(readOnly = true)
	public SellingHistoryResponse getSellingHistory(Long id) {
		User user = userService.findActiveUser(id);

		List<SellingBid> sellingBids = sellingRepository.findAllByUserAndStatus(
			user, DealStatus.BIDDING.getStatus()
		);

		List<Deal> ongoingDeals = dealRepository.findAllBySellerAndSellingStatus(
			user, DealStatus.UNDER_INSPECTION.getStatus()
		);

		List<Deal> completeDeals = dealRepository.findAllBySellerAndSellingStatus(
			user, DealStatus.PAYMENT_COMPLETED.getStatus()
		);

		return toSellingHistoryResponse(sellingBids, ongoingDeals, completeDeals);
	}

	private SellingHistoryResponse toSellingHistoryResponse(
		List<SellingBid> sellingBids,
		List<Deal> ongoingDeals,
		List<Deal> completeDeals
	) {
		List<SellingBidResponse> sellingBidResponses = new ArrayList<>();
		List<UserDealResponse> ongoingUserDealResponse = new ArrayList<>();
		List<UserDealResponse> completeUserDealResponse = new ArrayList<>();

		for (SellingBid sellingBid : sellingBids) {
			sellingBidResponses.add(sellingBid.toSellingBidResponse());
		}

		for (Deal deal : ongoingDeals) {
			ongoingUserDealResponse.add(deal.toUserDealResponse());
		}

		for (Deal deal : completeDeals) {
			completeUserDealResponse.add(deal.toUserDealResponse());
		}

		return new SellingHistoryResponse(
			sellingBidResponses,
			ongoingUserDealResponse,
			completeUserDealResponse
		);
	}

}
