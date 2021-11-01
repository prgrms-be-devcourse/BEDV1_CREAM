package org.prgrms.cream.domain.deal.repository;

import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingRepository extends JpaRepository<SellingBid, Long> {

}
