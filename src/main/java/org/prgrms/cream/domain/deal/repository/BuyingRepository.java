package org.prgrms.cream.domain.deal.repository;

import java.util.List;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingRepository extends JpaRepository<BuyingBid, Long> {

	List<BuyingBid> findTop2ByProductAndSizeAndStatusOrderBySuggestPriceDescCreatedDateAsc(
		Product product,
		String size,
		String dealStatus
	);
}
