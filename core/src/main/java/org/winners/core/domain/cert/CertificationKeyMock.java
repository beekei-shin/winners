package org.winners.core.domain.cert;

import java.time.LocalDateTime;
import java.util.UUID;

public class CertificationKeyMock {

    public static CertificationKey createKey() {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .id(UUID.randomUUID())
            .certificationType(CertificationType.PHONE_IDENTITY)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.plusMinutes(5))
            .build();
    }

    public static CertificationKey createExpiredKey() {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .id(UUID.randomUUID())
            .certificationType(CertificationType.PHONE_IDENTITY)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.minusDays(1))
            .build();
    }

    public static CertificationKey createCertifiedKey() {
        CertificationKey certificationKey = createKey();
        certificationKey.certify();
        return certificationKey;
    }

    public static CertificationKey createUsedKey() {
        CertificationKey certificationKey = createKey();
        certificationKey.use();
        return certificationKey;
    }

    public static CertificationKey createCertifiedAndUsedKey() {
        CertificationKey certificationKey = createKey();
        certificationKey.certify();
        certificationKey.use();
        return certificationKey;
    }

}
