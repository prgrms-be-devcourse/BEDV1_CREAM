package org.prgrms.cream.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INTERNAL_SERVER_ERROR("정의되지 않은 에러가 발생했습니다.", 500),
	INVALID_INPUT("올바른 입력 형식이 아닙니다.", 400),
	CONFLICT_ERROR("중복된 값입니다.", 409),
	NOT_FOUND_ERROR("찾을 수 없는 리소스입니다.", 404),
	;

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
