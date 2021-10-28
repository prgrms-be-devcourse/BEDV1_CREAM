package org.prgrms.cream.domain.product.domain;

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

@Getter
@Entity
@Table(name = "product_option")
public class ProductOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@Column(nullable = false)
	private String size;

	@Column(nullable = false, columnDefinition = "default 0")
	private int buyLowestPrice;

	@Column(nullable = false, columnDefinition = "default 0")
	private int sellHighestPrice;

	protected ProductOption() {
	}

	@Builder
	private ProductOption(Long id, Product product, String size) {
		this.id = id;
		this.product = product;
		this.size = size;
	}

	public void updateBuyLowestPrice(int price) {
		this.buyLowestPrice = price;
	}

	public void updateSellHighestPrice(int price) {
		this.sellHighestPrice = price;
	}
}
