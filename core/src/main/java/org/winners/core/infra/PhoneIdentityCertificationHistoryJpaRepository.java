package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistory;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistoryRepository;

public interface PhoneIdentityCertificationHistoryJpaRepository extends JpaRepository<PhoneIdentityCertificationHistory, Long>, PhoneIdentityCertificationHistoryRepository {
}
