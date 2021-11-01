package org.prgrms.cream.global.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private final T data;

	public ApiResponse(T data) {
		this.data = data;
	}

	public static <T> ApiResponse<T> of(T data) {
		return new ApiResponse<>(data);
	}
}
