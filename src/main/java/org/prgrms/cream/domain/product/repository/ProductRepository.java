package org.prgrms.cream.domain.product.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
	JpaSpecificationExecutor<Product> {

	List<Product> findAllByIsDeletedFalse();

	Optional<Product> findByIdAndIsDeletedFalse(Long id);

}
