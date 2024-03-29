package org.winners.core.domain.cert;

import java.util.Optional;

public interface PhoneIdentityCertificationHistoryRepository {
    PhoneIdentityCertificationHistory save(PhoneIdentityCertificationHistory phoneIdentityCertificationHistory);
    Optional<PhoneIdentityCertificationHistory> findByCertificationKeyAndPhoneNumber(CertificationKey certificationKey, String phoneNumber);
    Optional<PhoneIdentityCertificationHistory> findByCertificationKey(CertificationKey certificationKey);
}
