package org.prgrms.cream.domain.product.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.prgrms.cream.domain.product.dto.DetailResponse;
import org.prgrms.cream.domain.product.dto.ProductResponse;
import org.prgrms.cream.domain.product.dto.ProductsResponse;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@ApiOperation(value = "상품 리스트 조회",
		notes = "특정 조건에 맞는 상품들을 조회할 수 있습니다.")
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<ProductsResponse>>> getProducts(
		@RequestParam(required = false) Map<String, String> filter
	) {
		return ResponseEntity.ok(ApiResponse.of(productService.getProducts(filter)));
	}

	@ApiOperation(value = "특정 상품 조회",
		notes = "특정 상품의 기본 정보를 조회할 수 있습니다.")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.of(productService.getProduct(id)));
	}

	@ApiOperation(value = "특정 상품 상세 조회",
		notes = "특정 상품의 상세 정보(체결 거래, 판매/구매 입찰)를 조회할 수 있습니다.")
	@GetMapping("/{id}/details")
	public ResponseEntity<ApiResponse<DetailResponse>> getProductDetail(
		@PathVariable Long id,
		@RequestParam Optional<String> optSize
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				optSize
					.map(size -> productService.getProductDetailByOption(id, size))
					.orElse(productService.getProductDetail(id))
			)
		);
	}
}
