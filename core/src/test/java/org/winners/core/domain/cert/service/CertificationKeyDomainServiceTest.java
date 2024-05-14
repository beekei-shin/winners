package org.winners.core.domain.cert.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
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
        CertificationKey savedCertificationKey = CertificationKeyMock.createKey();
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
        CertificationKey savedCertificationKey = CertificationKeyMock.createKey();
        given(certificationKeyRepository.findById(any(UUID.class))).willReturn(Optional.of(savedCertificationKey));

        // when
        CertificationKey returnCertificationKey = certificationKeyDomainService.getSavedCertificationKey(UUID.randomUUID());

        // then
        assertThat(savedCertificationKey).isEqualTo(returnCertificationKey);
    }

    @Test
    @DisplayName("인증키 조회 - 존재하지 않는 인증키")
    void getSavedCertificationKey_notExistCertificationKey() {
        // given
        given(certificationKeyRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        // when
        UUID id = UUID.randomUUID();
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> certificationKeyDomainService.getSavedCertificationKey(id));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyRepository).findById(id);
    }

}