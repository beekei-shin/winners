package org.winners.core.domain.cert;

import java.time.LocalDateTime;
import java.util.UUID;

public class CertificationKeyMock {

    public static CertificationKey createCertificationKey(CertificationType certificationType, int expiredMinute) {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .id(UUID.randomUUID())
            .certificationType(certificationType)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.plusMinutes(expiredMinute))
            .build();
    }

    public static CertificationKey createExpiredCertificationKey(CertificationType certificationType) {
        LocalDateTime requestDatetime = LocalDateTime.now();
        return CertificationKey.builder()
            .id(UUID.randomUUID())
            .certificationType(certificationType)
            .requestDatetime(requestDatetime)
            .expiredDatetime(requestDatetime.minusDays(1))
            .build();
    }

    public static CertificationKey createCertifiedCertificationKey(CertificationType certificationType) {
        CertificationKey certificationKey = createCertificationKey(certificationType, 5);
        certificationKey.certify();
        return certificationKey;
    }

    public static CertificationKey createUsedCertificationKey(CertificationType certificationType) {
        CertificationKey certificationKey = createCertificationKey(certificationType, 5);
        certificationKey.use();
        return certificationKey;
    }

}
