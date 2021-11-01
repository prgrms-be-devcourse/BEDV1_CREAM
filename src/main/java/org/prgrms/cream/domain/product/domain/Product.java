package org.prgrms.cream.domain.product.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.prgrms.cream.domain.product.dto.ProductRequest;

@Getter
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String englishName;

	@Column(nullable = false)
	private String koreanName;

	@Column(nullable = false)
	private String modelNumber;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String image;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false, columnDefinition = "DATE")
	private String releaseDate;

	@Column(nullable = false)
	private int releasePrice;

	@Column(nullable = false, columnDefinition = "TINYINT default 0")
	private boolean isDeleted;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductOption> options = new ArrayList<>();

	protected Product() {
	}

	@Builder
	private Product(
		Long id,
		String brand,
		String englishName,
		String koreanName,
		String modelNumber,
		String image,
		String color,
		String releaseDate,
		int releasePrice
	) {
		this.id = id;
		this.brand = brand;
		this.englishName = englishName;
		this.koreanName = koreanName;
		this.modelNumber = modelNumber;
		this.image = image;
		this.color = color;
		this.releaseDate = releaseDate;
		this.releasePrice = releasePrice;
	}

	public void addOption(String option) {
		this.options.add(buildProductOption(option));
	}

	public void changeProductInfo(ProductRequest productRequest) {
		this.brand = productRequest.getBrand();
		this.englishName = productRequest.getEnglishName();
		this.koreanName = productRequest.getKoreanName();
		this.modelNumber = productRequest.getModelNumber();
		this.color = productRequest.getColor();
		this.releaseDate = productRequest.getReleaseDate();
		this.releasePrice = productRequest.getReleasePrice();
	}

	public void deleteProduct() {
		this.isDeleted = true;
	}

	private ProductOption buildProductOption(String option) {
		return ProductOption
			.builder()
			.product(this)
			.size(option)
			.build();
	}
}
