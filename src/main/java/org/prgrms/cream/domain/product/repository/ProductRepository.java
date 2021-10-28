package org.prgrms.cream.domain.product.repository;

import org.prgrms.cream.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
