package org.prgrms.cream.domain.product.service;

import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.dto.ProductRequest;
import org.prgrms.cream.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
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
}
