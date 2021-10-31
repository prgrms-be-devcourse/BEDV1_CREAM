package org.prgrms.cream.domain.deal.dto;

public record DealResponse(
	Long dealId,
	String productName,
	String size,
	Integer price
) {

	public static DealResponse of(Long id, String productName, String size, Integer price) {
		return new DealResponse(id, productName, size, price);
	}
}
