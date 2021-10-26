package org.prgrms.cream.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final String message;
	private final int status;

	public ErrorResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.getMessage(), errorCode.getStatus());
	}
}
