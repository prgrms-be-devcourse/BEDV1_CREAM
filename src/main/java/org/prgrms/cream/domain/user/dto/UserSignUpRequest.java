package org.prgrms.cream.domain.user.dto;

import lombok.Getter;
import org.prgrms.cream.domain.user.domain.User;

@Getter
public class UserSignUpRequest {

	private String nickname;
	private String email;
	private String phone;
	private String size;
	private String address;

	public User toEntity() {
		return User
			.builder()
			.nickname(nickname)
			.email(email)
			.phone(phone)
			.size(size)
			.address(address)
			.build();
	}
}
