package org.prgrms.cream.domain.deal.dto;

public record DealResponse(
	Long dealId,
	String productName,
	String size,
	int price,
	String createdDate
) {

	public static DealResponse of(
		Long id,
		String productName,
		String size,
		int price,
		String createdDate
	) {
		return new DealResponse(id, productName, size, price, createdDate);
	}
}
