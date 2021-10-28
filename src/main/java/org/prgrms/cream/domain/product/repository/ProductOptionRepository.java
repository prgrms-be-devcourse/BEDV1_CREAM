package org.prgrms.cream.domain.product.repository;

import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	boolean existsByProductAndSize(Product product, String size);
}
