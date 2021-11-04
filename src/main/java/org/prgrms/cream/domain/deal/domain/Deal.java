package org.prgrms.cream.domain.deal.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.prgrms.cream.domain.deal.dto.DealHistoryResponse;
import org.prgrms.cream.domain.deal.dto.DealResponse;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.dto.UserDealResponse;
import org.prgrms.cream.global.domain.BaseEntity;

@Getter
@Entity
@Table(name = "deal")
public class Deal extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private User buyer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private User seller;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private String size;

	@Column(nullable = false)
	private int price;

	@Column(columnDefinition = "VARCHAR(45) default '검수 중'")
	private String buyingStatus = "검수 중";

	@Column(columnDefinition = "VARCHAR(45) default '검수 중'")
	private String sellingStatus = "검수 중";

	@Column(nullable = false, columnDefinition = "TINYINT default 0")
	private boolean isFinished;

	protected Deal() {

	}

	@Builder
	private Deal(
		Long id,
		User buyer,
		User seller,
		Product product,
		String size,
		int price
	) {
		this.id = id;
		this.buyer = buyer;
		this.seller = seller;
		this.product = product;
		this.size = size;
		this.price = price;
	}

	public DealResponse toResponse() {
		return DealResponse.of(
			id,
			product.getEnglishName(),
			size,
			price,
			convertDateTime(this.getCreatedDate())
		);
	}

	public String getConvertCreatedDate() {
		return getCreatedDate().format(DateTimeFormatter.ofPattern("yy/MM/dd"));
	}

	public DealHistoryResponse toHistoryResponse() {
		return new DealHistoryResponse(
			id,
			product.getImage(),
			product.getEnglishName(),
			size,
			buyingStatus
		);
	}

	public DealHistoryResponse toHistoryDateResponse() {
		return new DealHistoryResponse(
			id,
			product.getImage(),
			product.getEnglishName(),
			size,
			buyingStatus,
			getConvertCreatedDate()
		);
	}

	private String convertDateTime(LocalDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}

	public UserDealResponse toUserDealResponse() {
		return new UserDealResponse(
			this.product.getImage(),
			this.product.getKoreanName(),
			this.size,
			this.price,
			convertDateTime(this.getCreatedDate()),
			this.getSellingStatus()
		);
	}
}
