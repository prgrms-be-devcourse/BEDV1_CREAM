package org.prgrms.cream.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import org.prgrms.cream.domain.user.domain.User;

@Getter
public class UserSignUpRequest {

	@NotBlank(message = "닉네임을 입력해 주세요.")
	private String nickname;

	@NotBlank(message = "이메일을 입력해 주세요.")
	@Email(message = "올바른 이메일 주소를 입력해 주세요.")
	private String email;

	@NotBlank(message = "휴대폰 번호를 입력해 주세요.")
	@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해 주세요.")
	private String phone;

	private int size;

	@NotBlank(message = "주소를 입력해 주세요.")
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
