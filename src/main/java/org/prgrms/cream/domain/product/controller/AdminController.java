package org.prgrms.cream.domain.product.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.prgrms.cream.domain.product.dto.ProductRequest;
import org.prgrms.cream.domain.product.dto.ProductResponse;
import org.prgrms.cream.domain.product.dto.ProductsResponse;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.global.response.ApiResponse;
import org.prgrms.cream.global.service.S3Uploader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/products")
public class AdminController {

	private static final String DIRECTORY = "products";

	private final S3Uploader s3Uploader;
	private final ProductService productService;

	public AdminController(
		S3Uploader s3Uploader,
		ProductService productService
	) {
		this.s3Uploader = s3Uploader;
		this.productService = productService;
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<ProductsResponse>>> getProducts(
		@RequestParam(required = false) Map<String, String> filter
	) {
		return ResponseEntity.ok(ApiResponse.of(productService.getProducts(filter)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.of(productService.getProduct(id)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Long>> registerProduct(
		@RequestPart MultipartFile file,
		@Valid @RequestPart ProductRequest request
	) throws IOException {
		String image = s3Uploader.upload(file, DIRECTORY);
		request.addImage(image);

		return ResponseEntity.ok(ApiResponse.of(productService.registerProduct(request)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Long>> modifyProduct(
		@PathVariable Long id,
		@Valid @RequestBody ProductRequest productRequest
	) {
		return ResponseEntity.ok(ApiResponse.of(productService.modifyProduct(id, productRequest)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
		productService.removeProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
