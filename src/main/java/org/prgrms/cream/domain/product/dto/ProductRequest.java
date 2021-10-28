package org.prgrms.cream.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import org.prgrms.cream.domain.product.domain.Product;

@Getter
public class ProductRequest {

	@NotBlank
	private String brand;

	@NotBlank
	private String englishName;

	@NotBlank
	private String koreanName;

	@NotBlank
	private String modelNumber;

	@NotBlank
	private String color;

	@NotBlank
	private int releasePrice;

	@NotBlank
	private String releaseDate;

	@JsonIgnore
	private String image;

	private List<String> sizes = new ArrayList<>();

	public ProductRequest() {
	}

	public Product toEntity() {
		return Product
			.builder()
			.brand(brand)
			.englishName(englishName)
			.koreanName(koreanName)
			.modelNumber(modelNumber)
			.releaseDate(releaseDate)
			.color(color)
			.releasePrice(releasePrice)
			.image(image)
			.build();
	}

	public void addImage(String image) {
		this.image = image;
	}
}
