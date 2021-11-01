package org.prgrms.cream.domain.product.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.prgrms.cream.domain.deal.dto.BuyingBidPriceResponse;
import org.prgrms.cream.domain.deal.dto.DealPriceResponse;
import org.prgrms.cream.domain.deal.dto.SellingBidPriceResponse;

@Getter
public class DetailResponse {

	private int recentDealPrice;
	private List<DealPriceResponse> dealPriceResponses = new ArrayList<>();
	private List<BuyingBidPriceResponse> buyingBidPriceResponses = new ArrayList<>();
	private List<SellingBidPriceResponse> sellingBidPriceResponses = new ArrayList<>();

	private DetailResponse() {
	}

	public DetailResponse(
		int recentDealPrice,
		List<DealPriceResponse> dealPriceResponses,
		List<BuyingBidPriceResponse> buyingBidPriceResponses,
		List<SellingBidPriceResponse> sellingBidPriceResponses
	) {
		this.recentDealPrice = recentDealPrice;
		this.dealPriceResponses = dealPriceResponses;
		this.buyingBidPriceResponses = buyingBidPriceResponses;
		this.sellingBidPriceResponses = sellingBidPriceResponses;
	}
}
