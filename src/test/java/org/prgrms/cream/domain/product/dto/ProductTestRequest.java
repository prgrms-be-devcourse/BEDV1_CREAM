package org.prgrms.cream.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.prgrms.cream.domain.product.domain.Product;

public class ProductTestRequest {

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

	@NotNull
	private int releasePrice;

	@NotBlank
	private String releaseDate;

	@JsonIgnore
	private String image;

	private List<String> sizes = new ArrayList<>();

	public ProductTestRequest() {
	}

	public ProductTestRequest(
		String brand,
		String englishName,
		String koreanName,
		String modelNumber,
		String color,
		int releasePrice,
		String releaseDate,
		List<String> sizes
	) {
		this.brand = brand;
		this.englishName = englishName;
		this.koreanName = koreanName;
		this.modelNumber = modelNumber;
		this.color = color;
		this.releasePrice = releasePrice;
		this.releaseDate = releaseDate;
		this.sizes = sizes;
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

	public String getBrand() {
		return brand;
	}

	public String getEnglishName() {
		return englishName;
	}

	public String getKoreanName() {
		return koreanName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public String getColor() {
		return color;
	}

	public int getReleasePrice() {
		return releasePrice;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getImage() {
		return image;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void addImage(String image) {
		this.image = image;
	}
}
