package org.winners.core.domain.cert;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.config.exception.AlreadyProcessedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.UnprocessedDataException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CertificationKeyTest {

    @Test
    @DisplayName("인증키 생성")
    void createKey() {
        CertificationKey certificationKey = CertificationKey.createKey(CertificationType.PHONE_IDENTITY, 5);
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
        CertificationKey notExpiredcertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        assertThat(notExpiredcertificationKey.isExpired()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredKey(UUID.randomUUID());
        assertThat(expiredCertificationKey.isExpired()).isTrue();
    }

    @Test
    @DisplayName("인증키 만료여부 확인")
    void expiredCheck() {
        CertificationKey notExpiredCertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        notExpiredCertificationKey.expiredCheck();
        assertThat(notExpiredCertificationKey.isExpired()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredKey(UUID.randomUUID());
        Throwable exception = assertThrows(AlreadyProcessedDataException.class, expiredCertificationKey::expiredCheck);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_CERTIFICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 인증")
    void certify() {
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        certificationKey.certify();

        assertThat(certificationKey.isCertified()).isTrue();
        assertThat(certificationKey.getCertifiedDatetime()).isNotNull();
    }

    @Test
    @DisplayName("인증키 인증여부 확인")
    void certifiedCheck() {
        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        notCertifiedCertificationKey.certifiedCheck();
        assertThat(notCertifiedCertificationKey.isCertified()).isFalse();

        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey(UUID.randomUUID());
        Throwable exception = assertThrows(AlreadyProcessedDataException.class, certifiedCertificationKey::certifiedCheck);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_CERTIFIED_CERTIFICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 미인증여부 확인")
    void notCertifiedCheck() {
        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        Throwable exception = assertThrows(UnprocessedDataException.class, notCertifiedCertificationKey::notCertifiedCheck);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_CERTIFIED_CERTIFICATION_KEY.getMessage());

        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey(UUID.randomUUID());
        certifiedCertificationKey.notCertifiedCheck();
        assertThat(certifiedCertificationKey.isCertified()).isTrue();
    }

    @Test
    @DisplayName("인증키 사용")
    void use() {
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        certificationKey.use();

        assertThat(certificationKey.isUsed()).isTrue();
        assertThat(certificationKey.getUsedDatetime()).isNotNull();
    }

    @Test
    @DisplayName("인증키 사용여부 확인")
    void usedCheck() {
        CertificationKey notUsedCertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        notUsedCertificationKey.usedCheck();
        assertThat(notUsedCertificationKey.isUsed()).isFalse();

        CertificationKey usedCertificationKey = CertificationKeyMock.createUsedKey(UUID.randomUUID());
        Throwable exception = assertThrows(AlreadyProcessedDataException.class, usedCertificationKey::usedCheck);
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_CERTIFICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 인증가능여부 확인")
    void possibleCertifyCheck() {
        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        notCertifiedCertificationKey.possibleCertifyCheck();
        assertThat(notCertifiedCertificationKey.isExpired()).isFalse();
        assertThat(notCertifiedCertificationKey.isCertified()).isFalse();
        assertThat(notCertifiedCertificationKey.isUsed()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredKey(UUID.randomUUID());
        Throwable expiredException = assertThrows(AlreadyProcessedDataException.class, expiredCertificationKey::possibleCertifyCheck);
        assertThat(expiredException.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_CERTIFICATION_KEY.getMessage());

        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey(UUID.randomUUID());
        Throwable certifiedException = assertThrows(AlreadyProcessedDataException.class, certifiedCertificationKey::possibleCertifyCheck);
        assertThat(certifiedException.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_CERTIFIED_CERTIFICATION_KEY.getMessage());

        CertificationKey usedCertificationKey = CertificationKeyMock.createUsedKey(UUID.randomUUID());
        Throwable usedException = assertThrows(AlreadyProcessedDataException.class, usedCertificationKey::possibleCertifyCheck);
        assertThat(usedException.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_CERTIFICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 사용가능여부 확인")
    void possibleUseCheck() {
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey(UUID.randomUUID());
        certifiedCertificationKey.possibleUseCheck();
        assertThat(certifiedCertificationKey.isCertified()).isTrue();
        assertThat(certifiedCertificationKey.isExpired()).isFalse();
        assertThat(certifiedCertificationKey.isUsed()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredKey(UUID.randomUUID());
        Throwable expiredException = assertThrows(AlreadyProcessedDataException.class, expiredCertificationKey::possibleUseCheck);
        assertThat(expiredException.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_CERTIFICATION_KEY.getMessage());

        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        Throwable certifiedException = assertThrows(UnprocessedDataException.class, notCertifiedCertificationKey::possibleUseCheck);
        assertThat(certifiedException.getMessage()).isEqualTo(ExceptionMessageType.NOT_CERTIFIED_CERTIFICATION_KEY.getMessage());

        CertificationKey usedCertificationKey = CertificationKeyMock.createCertifiedAndUsedKey(UUID.randomUUID());
        Throwable usedException = assertThrows(AlreadyProcessedDataException.class, usedCertificationKey::possibleUseCheck);
        assertThat(usedException.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_CERTIFICATION_KEY.getMessage());
    }

}