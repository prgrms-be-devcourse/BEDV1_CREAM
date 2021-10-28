package org.prgrms.cream.domain.product.controller;

import java.io.IOException;
import org.prgrms.cream.domain.product.dto.ProductRequest;
import org.prgrms.cream.domain.product.service.ProductService;
import org.prgrms.cream.global.service.S3Uploader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
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

	@PostMapping("/products")
	public ResponseEntity<Long> registerProduct(
		@RequestPart MultipartFile file,
		@RequestPart ProductRequest request
	) throws IOException {
		String image = s3Uploader.upload(file, DIRECTORY);
		request.addImage(image);

		return ResponseEntity.ok(productService.registerProduct(request));
	}

}
