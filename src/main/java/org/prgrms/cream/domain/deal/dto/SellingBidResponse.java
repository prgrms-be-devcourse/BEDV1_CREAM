package org.prgrms.cream.domain.deal.dto;

import lombok.Getter;

@Getter
public class SellingBidResponse {

	private String image;
	private String name;
	private String size;
	private int price;
	private String expiredDate;

	public SellingBidResponse(
		String image,
		String name,
		String size,
		int price,
		String expiredDate
	) {
		this.image = image;
		this.name = name;
		this.size = size;
		this.price = price;
		this.expiredDate = expiredDate;
	}
}
