package org.prgrms.cream.domain.product.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.BusinessException;

public class NotFoundProductOptionException extends BusinessException {

	public NotFoundProductOptionException(ErrorCode errorCode) {
		super(errorCode);
	}
}
