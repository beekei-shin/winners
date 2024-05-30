package org.winners.core.domain.cert;

import java.time.LocalDateTime;
import java.util.UUID;

public class CertificationKeyMock {

    public static CertificationKey createKey(UUID id) {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .id(id)
            .certificationType(CertificationType.PHONE_IDENTITY)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.plusMinutes(5))
            .build();
    }

    public static CertificationKey createExpiredKey(UUID id) {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .id(id)
            .certificationType(CertificationType.PHONE_IDENTITY)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.minusDays(1))
            .build();
    }

    public static CertificationKey createCertifiedKey(UUID id) {
        CertificationKey certificationKey = createKey(id);
        certificationKey.certify();
        return certificationKey;
    }

    public static CertificationKey createUsedKey(UUID id) {
        CertificationKey certificationKey = createKey(id);
        certificationKey.use();
        return certificationKey;
    }

    public static CertificationKey createCertifiedAndUsedKey(UUID id) {
        CertificationKey certificationKey = createKey(id);
        certificationKey.certify();
        certificationKey.use();
        return certificationKey;
    }

}
