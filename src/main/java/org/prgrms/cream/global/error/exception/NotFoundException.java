package org.prgrms.cream.global.error.exception;

import org.prgrms.cream.global.error.ErrorCode;

public class NotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public NotFoundException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
