package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationKeyRepository;

public interface CertificationKeyJpaRepository extends JpaRepository<CertificationKey, Long>, CertificationKeyRepository {
}
