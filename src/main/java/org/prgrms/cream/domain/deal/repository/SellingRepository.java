package org.prgrms.cream.domain.deal.repository;

import java.util.List;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.deal.dto.BidDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SellingRepository extends JpaRepository<SellingBid, Long> {

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
