package org.prgrms.cream.domain.deal.dto;

import javax.validation.constraints.Min;

public record BidRequest(
	@Min(30000) Integer price,
	@Min(1) Integer deadline,
	Long userId
) {

}
