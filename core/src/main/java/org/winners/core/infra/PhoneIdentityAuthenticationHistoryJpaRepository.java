package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.auth.PhoneIdentityAuthenticationHistory;
import org.winners.core.domain.auth.PhoneIdentityAuthenticationHistoryRepository;

public interface PhoneIdentityAuthenticationHistoryJpaRepository extends JpaRepository<PhoneIdentityAuthenticationHistory, Long>, PhoneIdentityAuthenticationHistoryRepository {
}
