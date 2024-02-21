package org.winners.core.domain.auth;

import java.util.Optional;
import java.util.UUID;

public interface PhoneIdentityAuthenticationHistoryRepository {
    Optional<PhoneIdentityAuthenticationHistory> findByAuthenticationKey(UUID authenticationKey);
}
