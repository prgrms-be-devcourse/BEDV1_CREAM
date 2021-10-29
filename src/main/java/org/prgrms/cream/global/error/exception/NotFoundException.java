package org.prgrms.cream.global.error.exception;

import org.prgrms.cream.global.error.ErrorCode;

public class NotFoundException extends BusinessException {

	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
