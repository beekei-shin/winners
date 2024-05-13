package org.winners.core.domain.cert;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationKeyTest {

    @Test
    @DisplayName("인증키 생성")
    void create() {
        CertificationKey certificationKey = CertificationKey.create(CertificationType.PHONE_IDENTITY, 5);
        assertThat(certificationKey.getCertificationType()).isEqualTo(CertificationType.PHONE_IDENTITY);
        assertThat(certificationKey.getExpiredDatetime()).isEqualTo(certificationKey.getRequestDatetime().plusMinutes(5));
        assertThat(certificationKey.isCertified()).isFalse();
        assertThat(certificationKey.getCertifiedDatetime()).isNull();
        assertThat(certificationKey.isUsed()).isFalse();
        assertThat(certificationKey.getUsedDatetime()).isNull();
    }

    @Test
    @DisplayName("인증키 만료여부 조회")
    void isExpired() {
        CertificationKey notExpiredcertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        assertThat(notExpiredcertificationKey.isExpired()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredCertificationKey(CertificationType.PHONE_IDENTITY);
        assertThat(expiredCertificationKey.isExpired()).isTrue();
    }

    @Test
    @DisplayName("인증키 인증")
    void certify() {
        CertificationKey certificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        certificationKey.certify();

        assertThat(certificationKey.isCertified()).isTrue();
        assertThat(certificationKey.getCertifiedDatetime()).isNotNull();
    }

    @Test
    @DisplayName("인증키 사용")
    void use() {
        CertificationKey certificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        certificationKey.use();

        assertThat(certificationKey.isUsed()).isTrue();
        assertThat(certificationKey.getUsedDatetime()).isNotNull();
    }

}