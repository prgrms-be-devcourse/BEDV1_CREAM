package org.prgrms.cream.domain.product.dto;

import lombok.Getter;
import org.prgrms.cream.domain.product.domain.Product;

@Getter
public class ProductsResponse {

	private Long id;
	private String brand;
	private String englishName;
	private String koreanName;
	private int straightBuyPrice;
	private int straightSellPrice;
	private String releaseDate;
	private String image;

	public ProductsResponse() {
	}

	public ProductsResponse(Product product, int sellLowestPrice, int buyHighestPrice) {
		this.id = product.getId();
		this.brand = product.getBrand();
		this.englishName = product.getEnglishName();
		this.koreanName = product.getKoreanName();
		this.straightBuyPrice = sellLowestPrice;
		this.straightSellPrice = buyHighestPrice;
		this.releaseDate = product.getReleaseDate();
		this.image = product.getImage();
	}
}
