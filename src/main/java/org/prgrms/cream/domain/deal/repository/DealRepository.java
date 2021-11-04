package org.prgrms.cream.domain.deal.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

	Optional<Deal> findFirstByProductAndIsFinishedTrueOrderByCreatedDateDesc(Product product);

	Optional<Deal> findFirstByProductAndSizeAndIsFinishedTrueOrderByCreatedDateDesc(
		Product product,
		String size
	);

	List<Deal> findAllByProductAndIsFinishedTrueOrderByCreatedDateDesc(Product product);

	List<Deal> findAllByProductAndSizeAndIsFinishedTrueOrderByCreatedDateDesc(
		Product product,
		String size
	);

	List<Deal> findAllBySellerAndSellingStatusAndIsFinishedFalse(User user, String status);

	List<Deal> findAllBySellerAndSellingStatusAndIsFinishedTrue(User user, String status);

	List<Deal> findAllBySellerAndIsFinishedFalse(User user);

	List<Deal> findAllBySellerAndIsFinishedTrue(User user);

	List<Deal> findAllByBuyerAndBuyingStatusAndIsFinishedFalse(User user, String status);

	List<Deal> findAllByBuyerAndIsFinishedFalse(User user);

	List<Deal> findAllByBuyerAndBuyingStatusAndIsFinishedTrue(User user, String status);

	List<Deal> findAllByBuyerAndIsFinishedTrue(User user);
}
