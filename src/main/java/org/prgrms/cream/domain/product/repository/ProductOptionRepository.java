package org.prgrms.cream.domain.product.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	boolean existsByProductAndSize(Product product, String size);

	Optional<ProductOption> findFirstByProductAndLowestPriceNotOrderByLowestPrice(
		Product product,
		int zero
	);

	Optional<ProductOption> findFirstByProductOrderByHighestPriceDesc(Product product);

	Optional<ProductOption> findByProductAndSize(Product product, String size);

	List<ProductOption> findByProduct(Product product);
}
