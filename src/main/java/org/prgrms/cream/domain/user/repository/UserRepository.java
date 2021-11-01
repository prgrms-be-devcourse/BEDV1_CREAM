package org.prgrms.cream.domain.user.repository;

import java.util.Optional;
import org.prgrms.cream.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdAndIsDeletedFalse(Long id);

	boolean existsUserByEmail(String email);
}
