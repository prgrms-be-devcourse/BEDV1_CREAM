package org.prgrms.cream.domain.product.repository;

import java.util.Optional;
import org.prgrms.cream.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByIdAndIsDeleted(Long id, boolean deleted);
}
