package org.prgrms.cream.domain.deal.repository;

import java.util.List;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.dto.BidDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuyingRepository extends JpaRepository<BuyingBid, Long> {

	@Query(value =
		"SELECT b.size, b.suggest_price as price, COUNT(*) as quantity "
			+ "FROM buying_bid b "
			+ "WHERE b.product_id = ?1 "
			+ "GROUP BY b.product_id, b.size, b.suggest_price "
			+ "ORDER BY b.suggest_price DESC", nativeQuery = true)
	List<BidDetail> findAllByProductGroupBy(Long productId);

	@Query(value =
		"SELECT b.size, b.suggest_price as price, COUNT(*) as quantity "
			+ "FROM buying_bid b "
			+ "WHERE b.product_id = ?1 AND b.size = ?2 "
			+ "GROUP BY b.product_id, b.size, b.suggest_price "
			+ "ORDER BY b.suggest_price DESC", nativeQuery = true)
	List<BidDetail> findAllByProductAndSizeGroupBy(Long productId, String size);
}
