package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.auth.AuthenticationHistory;
import org.winners.core.domain.auth.AuthenticationHistoryRepository;

public interface AuthenticationHistoryJpaRepository extends JpaRepository<AuthenticationHistory, Long>, AuthenticationHistoryRepository {
}
