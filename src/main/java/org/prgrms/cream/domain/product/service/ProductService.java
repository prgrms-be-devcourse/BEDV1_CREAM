package org.prgrms.cream.domain.product.service;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.dto.ProductRequest;
import org.prgrms.cream.domain.product.dto.ProductsResponse;
import org.prgrms.cream.domain.product.exception.NotFoundProductException;
import org.prgrms.cream.domain.product.repository.ProductOptionRepository;
import org.prgrms.cream.domain.product.repository.ProductRepository;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	private static final int NO_BID = 0;
	private static final int ZERO = 0;

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;

	public ProductService(
		ProductRepository productRepository,
		ProductOptionRepository productOptionRepository
	) {
		this.productRepository = productRepository;
		this.productOptionRepository = productOptionRepository;
	}

	@Transactional(readOnly = true)
	public List<ProductsResponse> getProducts() {
		return productRepository
			.findAllByIsDeletedFalse()
			.stream()
			.map(this::toProductResponse)
			.toList();
	}

	@Transactional
	public Long registerProduct(ProductRequest productRequest) {
		Product product = productRequest.toEntity();
		for (String size : productRequest.getSizes()) {
			product.addOption(size);
		}

		return productRepository
			.save(product)
			.getId();
	}

	@Transactional
	public Long modifyProduct(Long id, ProductRequest productRequest) {
		Product product = findActiveProduct(id);
		product.changeProductInfo(productRequest);
		for (String size : productRequest.getSizes()) {
			modifyOption(product, size);
		}
		return product.getId();
	}

	private void modifyOption(Product product, String size) {
		boolean isExist = productOptionRepository.existsByProductAndSize(product, size);
		if (!isExist) {
			product.addOption(size);
		}
	}

	private Product findActiveProduct(Long id) {
		return productRepository
			.findByIdAndIsDeletedFalse(id)
			.orElseThrow(() -> new NotFoundProductException(ErrorCode.NOT_FOUND_RESOURCE));
	}

	private ProductsResponse toProductResponse(Product product) {
		Optional<ProductOption> optBuyLowestPrice = productOptionRepository
			.findFirstByProductAndBuyLowestPriceNotOrderByBuyLowestPrice(product, ZERO);

		Optional<ProductOption> optSellHighestPrice = productOptionRepository
			.findFirstByProductOrderBySellHighestPriceAsc(product);

		int buyLowestPrice = NO_BID;
		if (optBuyLowestPrice.isPresent()) {
			buyLowestPrice = optBuyLowestPrice
				.get()
				.getBuyLowestPrice();
		}

		int sellHighestPrice = NO_BID;
		if (optSellHighestPrice.isPresent()) {
			sellHighestPrice = optSellHighestPrice
				.get()
				.getSellHighestPrice();
		}

		return new ProductsResponse(product, buyLowestPrice, sellHighestPrice);
	}
}
