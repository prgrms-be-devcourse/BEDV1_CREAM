package org.prgrms.cream.domain.user.domain;

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.prgrms.cream.domain.user.dto.UserResponse;
import org.prgrms.cream.domain.user.dto.UserUpdateRequest;
import org.prgrms.cream.domain.user.exception.InvalidArgumentException;
import org.prgrms.cream.global.error.ErrorCode;

@Entity
@Table(name = "user")
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "닉네임을 입력해 주세요.")
	@Column(nullable = false, unique = true, length = 45)
	private String nickname;

	@NotBlank(message = "이메일을 입력해 주세요.")
	@Email(message = "올바른 이메일 주소를 입력해 주세요.")
	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@NotBlank(message = "휴대폰 번호를 입력해 주세요.")
	@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해 주세요.")
	@Column(nullable = false, unique = true, length = 45)
	private String phone;

	private String size;

	@NotBlank(message = "주소를 입력해 주세요.")
	@Column(nullable = false, length = 100)
	private String address;

	@Column(nullable = false, columnDefinition = "TINYINT default false")
	private boolean isDeleted;

	protected User() {
	}

	@Builder
	private User(
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

	enum UpdateInfo {
		NICKNAME("nickname"),
		EMAIL("email"),
		PHONE("phone"),
		SIZE("size"),
		ADDRESS("address");

		private String option;

		UpdateInfo(String option) {
			this.option = option;
		}

		static UpdateInfo getUpdateOption(String input) {
			return Arrays
				.stream(UpdateInfo.values())
				.filter(u -> u.option.equals(input))
				.findFirst()
				.orElseThrow(() -> new InvalidArgumentException(ErrorCode.INVALID_INPUT));
		}
	}

	public void updateUser(UserUpdateRequest userUpdateRequest) {
		switch (UpdateInfo.getUpdateOption(userUpdateRequest.getOption())) {
			case NICKNAME -> this.nickname = userUpdateRequest.getValue();
			case EMAIL -> this.email = userUpdateRequest.getValue();
			case PHONE -> this.phone = userUpdateRequest.getValue();
			case ADDRESS -> this.address = userUpdateRequest.getValue();
			case SIZE -> this.size = userUpdateRequest.getValue();
		}
	}

	public UserResponse toResponse() {
		return UserResponse
			.builder()
			.id(id)
			.nickname(nickname)
			.size(size)
			.phone(phone)
			.email(email)
			.address(address)
			.build();
	}

	public void deleteUser() {
		this.isDeleted = true;
	}
}
