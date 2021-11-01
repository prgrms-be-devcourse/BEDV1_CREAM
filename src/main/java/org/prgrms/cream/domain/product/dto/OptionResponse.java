package org.prgrms.cream.domain.product.dto;

import lombok.Getter;

@Getter
public class OptionResponse {

	private String size;
	private int straightBuyPrice;
	private int straightSellPrice;

	public OptionResponse(String size, int straightBuyPrice, int straightSellPrice) {
		this.size = size;
		this.straightBuyPrice = straightBuyPrice;
		this.straightSellPrice = straightSellPrice;
	}
}
