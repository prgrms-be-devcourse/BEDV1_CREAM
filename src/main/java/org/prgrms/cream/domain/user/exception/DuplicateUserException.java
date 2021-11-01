package org.prgrms.cream.domain.user.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.BusinessException;

public class DuplicateUserException extends BusinessException {

	public DuplicateUserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
