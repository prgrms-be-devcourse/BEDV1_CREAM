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
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.global.domain.BaseEntity;

@Getter
@Entity
@Table(name = "buying_bid")
public class BuyingBid extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private String size;

	@Column(nullable = false)
	private int suggestPrice;

	@Column(nullable = false)
	private int deadline;

	@Column(columnDefinition = "VARCHAR(45) default '입찰 중'")
	private String status = "입찰 중";

	protected BuyingBid() {
	}

	@Builder
	private BuyingBid(
		Long id,
		User user,
		Product product,
		String size,
		int suggestPrice,
		int deadline
	) {
		this.id = id;
		this.user = user;
		this.product = product;
		this.size = size;
		this.suggestPrice = suggestPrice;
		this.deadline = deadline;
	}

	public void changeStatus(DealStatus status) {
		this.status = status.getStatus();
	}

	public void cancel() {
		this.status = DealStatus.BID_CANCELLED.getStatus();
	}
}
