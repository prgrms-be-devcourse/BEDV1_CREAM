package org.prgrms.cream.domain.product.service;

import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.dto.ProductRequest;
import org.prgrms.cream.domain.product.exception.NotFoundProductException;
import org.prgrms.cream.domain.product.repository.ProductOptionRepository;
import org.prgrms.cream.domain.product.repository.ProductRepository;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;

	public ProductService(
		ProductRepository productRepository,
		ProductOptionRepository productOptionRepository
	) {
		this.productRepository = productRepository;
		this.productOptionRepository = productOptionRepository;
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
			.findByIdAndIsDeleted(id, false)
			.orElseThrow(() -> new NotFoundProductException(ErrorCode.NOT_FOUND_RESOURCE));
	}
}
