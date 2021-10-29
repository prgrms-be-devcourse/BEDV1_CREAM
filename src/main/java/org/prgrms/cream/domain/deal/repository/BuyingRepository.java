package org.prgrms.cream.domain.deal.repository;

import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyingRepository extends JpaRepository<BuyingBid, Long> {

}
