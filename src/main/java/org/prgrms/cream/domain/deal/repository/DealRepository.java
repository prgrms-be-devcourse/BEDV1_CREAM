package org.prgrms.cream.domain.deal.repository;

import org.prgrms.cream.domain.deal.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

}
