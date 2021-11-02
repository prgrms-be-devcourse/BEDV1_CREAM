package org.prgrms.cream.domain.deal.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

	Optional<Deal> findFirstByProductOrderByCreatedDateDesc(Product product);

	Optional<Deal> findFirstByProductAndSizeOrderByCreatedDateDesc(Product product, String size);

	List<Deal> findAllByProductOrderByCreatedDateDesc(Product product);

	List<Deal> findAllByProductAndSizeOrderByCreatedDateDesc(Product product, String size);

	List<Deal> findAllBySellerAndSellingStatus(User user, String status);
}
