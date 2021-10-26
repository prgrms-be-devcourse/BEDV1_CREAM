package org.prgrms.cream.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INTERNAL_SERVER_ERROR("정의되지 않은 에러가 발생했습니다.", 500),
	;

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
