package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.auth.AuthenticationKey;
import org.winners.core.domain.auth.AuthenticationKeyRepository;

public interface AuthenticationKeyJpaRepository extends JpaRepository<AuthenticationKey, Long>, AuthenticationKeyRepository {
}
