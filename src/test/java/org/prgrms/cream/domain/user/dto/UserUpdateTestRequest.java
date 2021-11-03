package org.prgrms.cream.domain.user.dto;

public class UserUpdateTestRequest {

	private String option;
	private String value;

	public UserUpdateTestRequest(String option, String value) {
		this.option = option;
		this.value = value;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
