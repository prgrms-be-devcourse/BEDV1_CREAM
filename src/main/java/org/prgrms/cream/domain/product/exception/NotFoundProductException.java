package org.prgrms.cream.domain.product.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.NotFoundException;

public class NotFoundProductException extends NotFoundException {

	public NotFoundProductException(ErrorCode errorCode) {
		super(errorCode);
	}
}
