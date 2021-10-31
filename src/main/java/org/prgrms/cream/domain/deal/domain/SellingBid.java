package org.prgrms.cream.domain.deal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.global.domain.BaseEntity;

@Entity
@Table(name = "seling_bid")
@Getter
public class SellingBid extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false, length = 45)
	private String size;

	@Column(nullable = false)
	private int suggestPrice;

	@Column(length = 45, columnDefinition = "default '입찰 전'")
	private String status;

	@Column(nullable = false)
	private int deadline;

	protected SellingBid() {
	}

	@Builder
	public SellingBid(
		Long id,
		User user,
		Product product,
		String size,
		int suggestPrice,
		String status,
		int deadline
	) {
		this.id = id;
		this.user = user;
		this.product = product;
		this.size = size;
		this.suggestPrice = suggestPrice;
		this.status = status;
		this.deadline = deadline;
	}
}
