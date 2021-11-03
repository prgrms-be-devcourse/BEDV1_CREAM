package org.prgrms.cream.domain.deal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealHistoryResponse {

	private final Long id;
	private final String image;
	private final String productName;
	private final String size;
	private final String status;
	private String buyDate;

	public DealHistoryResponse(
		Long id,
		String image,
		String productName,
		String size,
		String status
	) {
		this.id = id;
		this.image = image;
		this.productName = productName;
		this.size = size;
		this.status = status;
	}

	public DealHistoryResponse(
		Long id,
		String image,
		String productName,
		String size,
		String status,
		String buyDate
	) {
		this.id = id;
		this.image = image;
		this.productName = productName;
		this.size = size;
		this.status = status;
		this.buyDate = buyDate;
	}
}
