package org.prgrms.cream.domain.deal.dto;

import lombok.Getter;

@Getter
public class DealHistoryResponse {

	private final Long id;
	private final String img;
	private final String productName;
	private final String size;
	private final String status;

	public DealHistoryResponse(
		Long id,
		String img,
		String productName,
		String size,
		String status
	) {
		this.id = id;
		this.img = img;
		this.productName = productName;
		this.size = size;
		this.status = status;
	}
}
