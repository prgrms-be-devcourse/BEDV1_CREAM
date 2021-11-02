package org.prgrms.cream.domain.user.dto;

public class UserSignUpTestRequest {

	private String nickname;
	private String email;
	private String phone;
	private String size;
	private String address;

	public UserSignUpTestRequest(
		String nickname,
		String email,
		String phone,
		String size,
		String address
	) {
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.size = size;
		this.address = address;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
