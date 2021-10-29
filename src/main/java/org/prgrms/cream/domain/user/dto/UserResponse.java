package org.prgrms.cream.domain.user.dto;

import lombok.Getter;
import org.prgrms.cream.domain.user.domain.User;

@Getter
public class UserResponse {

	private Long id;
	private String nickname;
	private String email;
	private String phone;
	private String size;
	private String address;

	public UserResponse(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.size = user.getSize();
		this.address = user.getAddress();
	}
}
