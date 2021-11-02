package org.prgrms.cream.domain.user.dto;

import lombok.Getter;

@Getter
public class UserDealResponse {

	private String image;
	private String name;
	private String size;
	private int price;
	private String dealDate;
	private String status;

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
