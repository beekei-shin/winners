package org.winners.core.domain.cert.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.exception.AlreadyProcessedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.exception.UnprocessedDataException;
import org.winners.core.domain.DomainServiceTest;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationKeyMock;
import org.winners.core.domain.cert.CertificationKeyRepository;
import org.winners.core.domain.cert.CertificationType;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CertificationKeyDomainServiceTest extends DomainServiceTest {

    private CertificationKeyDomainService certificationKeyDomainService;

    private CertificationKeyRepository certificationKeyRepository;

    @BeforeAll
    public void beforeAll() {
        this.certificationKeyRepository = Mockito.mock(CertificationKeyRepository.class);
        this.certificationKeyDomainService = new CertificationKeyDomainService(this.certificationKeyRepository);
    }

    @Test
    @DisplayName("인증키 생성")
    void createCertificationKey() {
        // given
        CertificationKey savedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        given(certificationKeyRepository.save(any(CertificationKey.class))).willReturn(savedCertificationKey);

        // when
        CertificationKey createdCertificationKey = certificationKeyDomainService.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);

        // then
        assertThat(savedCertificationKey).isEqualTo(createdCertificationKey);
    }

    @Test
    @DisplayName("인증키 조회")
    void getSavedCertificationKey() {
        // given
        CertificationKey savedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        given(certificationKeyRepository.findById(any(UUID.class))).willReturn(Optional.of(savedCertificationKey));

        // when
        CertificationKey returnCertificationKey = certificationKeyDomainService.getSavedCertificationKey(UUID.randomUUID());

        // then
        assertThat(savedCertificationKey).isEqualTo(returnCertificationKey);
    }

    @Test
    @DisplayName("인증키 조회 - 존재하지 않는 인증키")
    void getSavedCertificationKey_notExi() {
        // given
        given(certificationKeyRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when
        UUID id = UUID.randomUUID();
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> certificationKeyDomainService.getSavedCertificationKey(id));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_AUTHENTICATION_KEY.getMessage());
        verify(certificationKeyRepository).findById(id);
    }

    @Test
    @DisplayName("인증키 인증여부 확인")
    void certifiedCheck() {
        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        certificationKeyDomainService.certifiedCheck(notCertifiedCertificationKey);
        assertThat(notCertifiedCertificationKey.isCertified()).isFalse();

        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.certifiedCheck(certifiedCertificationKey));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_CERTIFIED_AUTHENTICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 미인증여부 확인")
    void notCertifiedCheck() {
        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        Throwable exception = assertThrows(UnprocessedDataException.class,
            () -> certificationKeyDomainService.notCertifiedCheck(notCertifiedCertificationKey));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_CERTIFIED_AUTHENTICATION_KEY.getMessage());

        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedCertificationKey(CertificationType.PHONE_IDENTITY);
        certificationKeyDomainService.notCertifiedCheck(certifiedCertificationKey);
        assertThat(certifiedCertificationKey.isCertified()).isTrue();
    }

    @Test
    @DisplayName("인증키 사용여부 확인")
    void usedCheck() {
        CertificationKey notUsedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        certificationKeyDomainService.usedCheck(notUsedCertificationKey);
        assertThat(notUsedCertificationKey.isUsed()).isFalse();

        CertificationKey usedCertificationKey = CertificationKeyMock.createUsedCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.usedCheck(usedCertificationKey));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_AUTHENTICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 만료여부 확인")
    void expiredCheck() {
        CertificationKey notExpiredCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        certificationKeyDomainService.expiredCheck(notExpiredCertificationKey);
        assertThat(notExpiredCertificationKey.isExpired()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.expiredCheck(expiredCertificationKey));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_AUTHENTICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 인증가능여부 확인")
    void possibleCertifyCheck() {
        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        certificationKeyDomainService.possibleCertifyCheck(notCertifiedCertificationKey);
        assertThat(notCertifiedCertificationKey.isExpired()).isFalse();
        assertThat(notCertifiedCertificationKey.isCertified()).isFalse();
        assertThat(notCertifiedCertificationKey.isUsed()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable expiredException = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.possibleCertifyCheck(expiredCertificationKey));
        assertThat(expiredException.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_AUTHENTICATION_KEY.getMessage());

        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable certifiedException = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.possibleCertifyCheck(certifiedCertificationKey));
        assertThat(certifiedException.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_CERTIFIED_AUTHENTICATION_KEY.getMessage());

        CertificationKey usedCertificationKey = CertificationKeyMock.createUsedCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable usedException = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.possibleCertifyCheck(usedCertificationKey));
        assertThat(usedException.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_AUTHENTICATION_KEY.getMessage());
    }

    @Test
    @DisplayName("인증키 사용가능여부 확인")
    void possibleUseCheck() {
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedCertificationKey(CertificationType.PHONE_IDENTITY);
        certificationKeyDomainService.possibleUseCheck(certifiedCertificationKey);
        assertThat(certifiedCertificationKey.isCertified()).isTrue();
        assertThat(certifiedCertificationKey.isExpired()).isFalse();
        assertThat(certifiedCertificationKey.isUsed()).isFalse();

        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredCertificationKey(CertificationType.PHONE_IDENTITY);
        Throwable expiredException = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.possibleUseCheck(expiredCertificationKey));
        assertThat(expiredException.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_AUTHENTICATION_KEY.getMessage());

        CertificationKey notCertifiedCertificationKey = CertificationKeyMock.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        Throwable certifiedException = assertThrows(UnprocessedDataException.class,
            () -> certificationKeyDomainService.possibleUseCheck(notCertifiedCertificationKey));
        assertThat(certifiedException.getMessage()).isEqualTo(ExceptionMessageType.NOT_CERTIFIED_AUTHENTICATION_KEY.getMessage());

        certifiedCertificationKey.use();
        Throwable usedException = assertThrows(AlreadyProcessedDataException.class,
            () -> certificationKeyDomainService.possibleUseCheck(certifiedCertificationKey));
        assertThat(usedException.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_AUTHENTICATION_KEY.getMessage());
    }

}