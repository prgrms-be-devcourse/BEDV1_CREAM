package org.prgrms.cream.domain.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "user")
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nickname;

	private String email;

	private String phone;

	@Range(min = 220, max = 300)
	private int size;

	private String address;

	private boolean isDeleted = false;

	protected User() {
	}

	@Builder
	private User(Long id, String nickname, String email, String phone, int size, String address) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.size = size;
		this.address = address;
	}
}
