package org.prgrms.cream.domain.user.repository;

import java.util.Optional;
import org.prgrms.cream.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsUserByEmail(String email);

	Optional<User> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
