package org.winners.core.domain.cert;

import java.util.Optional;
import java.util.UUID;

public interface CertificationKeyRepository {
    CertificationKey save(CertificationKey certificationKey);
    Optional<CertificationKey> findById(UUID id);
}
