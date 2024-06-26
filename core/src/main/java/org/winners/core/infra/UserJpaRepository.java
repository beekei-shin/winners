package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.user.User;
import org.winners.core.domain.user.UserRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
}
