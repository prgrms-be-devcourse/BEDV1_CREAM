package org.prgrms.cream.domain.deal.dto;

import lombok.Getter;

@Getter
public class DealPriceResponse {

	private String size;
	private int dealPrice;
	private String dealDate;

	public DealPriceResponse(String size, int dealPrice, String dealDate) {
		this.size = size;
		this.dealPrice = dealPrice;
		this.dealDate = dealDate;
	}
}
