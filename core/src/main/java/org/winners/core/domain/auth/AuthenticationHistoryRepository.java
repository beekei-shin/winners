package org.winners.core.domain.auth;

public interface AuthenticationHistoryRepository {
    AuthenticationHistory save(AuthenticationHistory authenticationHistory);
}
