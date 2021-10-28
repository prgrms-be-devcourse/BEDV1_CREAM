package org.prgrms.cream.domain.user.repository;

import org.prgrms.cream.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsUserByEmail(String email);
}
