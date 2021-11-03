package org.prgrms.cream.domain.user.dto;

import lombok.Getter;

@Getter
public class UserDealResponse {

	private final String image;
	private final String name;
	private final String size;
	private final int price;
	private final String dealDate;
	private final String status;

	public UserDealResponse(
		String image,
		String name,
		String size,
		int price,
		String dealDate,
		String status
	) {
		this.image = image;
		this.name = name;
		this.size = size;
		this.price = price;
		this.dealDate = dealDate;
		this.status = status;
	}
}
