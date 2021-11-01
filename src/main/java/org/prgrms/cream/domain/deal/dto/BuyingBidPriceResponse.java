package org.prgrms.cream.domain.deal.dto;

import lombok.Getter;

@Getter
public class BuyingBidPriceResponse {

	private String size;
	private int buyingPrice;
	private int quantity;

	public BuyingBidPriceResponse(String size, int buyingPrice, int quantity) {
		this.size = size;
		this.buyingPrice = buyingPrice;
		this.quantity = quantity;
	}
}
