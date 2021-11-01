package org.prgrms.cream.domain.deal.dto;

import lombok.Getter;

@Getter
public class SellingBidPriceResponse {

	private String size;
	private int sellingPrice;
	private int quantity;

	public SellingBidPriceResponse(String size, int sellingPrice, int quantity) {
		this.size = size;
		this.sellingPrice = sellingPrice;
		this.quantity = quantity;
	}
}
