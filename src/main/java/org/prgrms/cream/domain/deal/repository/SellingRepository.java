package org.prgrms.cream.domain.deal.repository;

import java.util.List;
import java.util.Optional;
import org.prgrms.cream.domain.deal.dto.BidDetail;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingRepository extends JpaRepository<SellingBid, Long> {

	boolean existsByUserAndProductAndSize(User user, Product product, String size);
  
  Optional<SellingBid> findByUserAndProductAndSize(User user, Product product, String size);

	List<SellingBid> findFirst2ByProductAndSizeAndStatusOrderBySuggestPriceAscCreatedDateAsc(
		Product product,
		String size,
		String status
	);
  
  @Query(
		value =
			"SELECT s.size, s.suggest_price as price, COUNT(*) as quantity "
				+ "FROM selling_bid s "
				+ "WHERE s.product_id = ?1 "
				+ "GROUP BY s.product_id, s.size, s.suggest_price "
				+ "ORDER BY s.suggest_price",
		nativeQuery = true
	)
	List<BidDetail> findAllByProductGroupBy(Long productId);

	@Query(
		value =
			"SELECT s.size, s.suggest_price as price, COUNT(*) as quantity "
				+ "FROM selling_bid s "
				+ "WHERE s.product_id = ?1 AND s.size = ?2 "
				+ "GROUP BY s.product_id, s.size, s.suggest_price "
				+ "ORDER BY s.suggest_price",
		nativeQuery = true
	)
	List<BidDetail> findAllByProductAndSizeGroupBy(Long productId, String size);
}
