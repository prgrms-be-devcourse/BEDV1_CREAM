package org.prgrms.cream.domain.product.dto;

import lombok.Getter;

@Getter
public class OptionResponse {

	private String size;
	private int straightBuyPrice;

	public OptionResponse(String size, int straightBuyPrice) {
		this.size = size;
		this.straightBuyPrice = straightBuyPrice;
	}
}
