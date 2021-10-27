package org.prgrms.cream.domain.user.domain;

import javax.persistence.Column;
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

	@Column(nullable = false,unique = true,length = 45)
	private String nickname;

	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@Column(nullable = false, unique = true,length = 45)
	private String phone;

	@Range(min = 220, max = 300)
	private int size;

	@Column(nullable = false, length = 100)
	private String address;

	@Column(nullable = false)
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
