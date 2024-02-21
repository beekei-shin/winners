package org.winners.core.domain.auth;

import java.util.Optional;
import java.util.UUID;

public interface AuthenticationKeyRepository {
    Optional<AuthenticationKey> findByAuthenticationKey(UUID authenticationKey);
}
