package org.prgrms.cream.domain.user.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.BusinessException;

public class NotFoundUserException extends BusinessException {

	public NotFoundUserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
