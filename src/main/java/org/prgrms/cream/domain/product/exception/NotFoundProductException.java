package org.prgrms.cream.domain.product.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.BusinessException;

public class NotFoundProductException extends BusinessException {

	public NotFoundProductException(ErrorCode errorCode) {
		super(errorCode);
	}
}
