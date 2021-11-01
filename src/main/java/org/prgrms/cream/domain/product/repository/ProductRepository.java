package org.prgrms.cream.domain.product.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByIsDeletedFalse();

	Optional<Product> findByIdAndIsDeletedFalse(Long id);
}
