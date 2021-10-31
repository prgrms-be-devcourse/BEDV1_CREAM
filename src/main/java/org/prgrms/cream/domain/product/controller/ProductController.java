package org.prgrms.cream.domain.product.controller;

import java.util.List;
import org.prgrms.cream.domain.product.dto.ProductResponse;
import org.prgrms.cream.domain.product.dto.ProductsResponse;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(
		ProductService productService
	) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductsResponse>>> getProducts() {
		return ResponseEntity.ok(ApiResponse.of(productService.getProducts()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.of(productService.getProduct(id)));
	}
}
