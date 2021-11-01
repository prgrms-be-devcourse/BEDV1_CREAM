package org.prgrms.cream.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

	private Long id;
	private String nickname;
	private String email;
	private String phone;
	private String size;
	private String address;

	@Builder
	public UserResponse(
		Long id,
		String nickname,
		String email,
		String phone,
		String size,
		String address
	) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.size = size;
		this.address = address;
	}
}
