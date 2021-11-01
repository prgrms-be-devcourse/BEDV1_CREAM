package org.prgrms.cream.domain.product.dto;

import java.util.List;
import lombok.Getter;
import org.prgrms.cream.domain.product.domain.Product;

@Getter
public class ProductResponse {

	private Long id;
	private String brand;
	private String englishName;
	private String koreanName;
	private String modelNumber;
	private String releaseDate;
	private String color;
	private int releasePrice;
	private String image;
	private List<OptionResponse> options;

	private ProductResponse() {
	}

	public ProductResponse(
		Product product,
		List<OptionResponse> options
	) {
		this.id = product.getId();
		this.brand = product.getBrand();
		this.englishName = product.getEnglishName();
		this.koreanName = product.getKoreanName();
		this.modelNumber = product.getModelNumber();
		this.releaseDate = product.getReleaseDate();
		this.color = product.getColor();
		this.releasePrice = product.getReleasePrice();
		this.image = product.getImage();
		this.options = options;
	}
}
