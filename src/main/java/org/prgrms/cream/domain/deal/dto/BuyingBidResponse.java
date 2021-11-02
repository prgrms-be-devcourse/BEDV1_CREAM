package org.prgrms.cream.domain.deal.dto;

public record BuyingBidResponse(
	Long id,
	String image,
	String productName,
	String size,
	int suggestPrice,
	String createdDate
) {

}
