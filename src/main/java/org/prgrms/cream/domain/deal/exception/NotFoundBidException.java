package org.prgrms.cream.domain.deal.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.NotFoundException;

public class NotFoundBidException extends NotFoundException {

	public NotFoundBidException(ErrorCode errorCode) {
		super(errorCode);
	}
}
