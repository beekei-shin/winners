package org.winners.core.domain.cert.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.InvalidDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.DomainServiceTest;
import org.winners.core.domain.cert.*;
import org.winners.core.domain.cert.service.dto.CertificationInfoDTO;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class PhoneIdentityCertificationDomainServiceTest extends DomainServiceTest {

    private PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;
    private PasswordEncoder passwordEncoder;
    private PhoneIdentityCertificationHistoryRepository phoneIdentityCertificationHistoryRepository;

    @BeforeEach
    public void beforeEach() {
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        this.phoneIdentityCertificationHistoryRepository = Mockito.mock(PhoneIdentityCertificationHistoryRepository.class);
        this.phoneIdentityCertificationDomainService = new PhoneIdentityCertificationDomainService(this.passwordEncoder, this.phoneIdentityCertificationHistoryRepository);
    }

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 전송")
    void sendOtpNumber() {
        // given
        String encryptOtpNumber = "encryptOtpNumber";
        given(passwordEncoder.encode(anyString())).willReturn(encryptOtpNumber);

        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        PhoneIdentityCertificationHistory savedPhoneIdentityCertificationHistory = PhoneIdentityCertificationHistoryMock.createHistory(certificationKey);
        given(phoneIdentityCertificationHistoryRepository.save(any(PhoneIdentityCertificationHistory.class))).willReturn(savedPhoneIdentityCertificationHistory);

        // when
        UUID certificationKeyId = phoneIdentityCertificationDomainService.sendOtpNumber(
            savedPhoneIdentityCertificationHistory.getCertificationKey(), SendOtpNumberParameterDTO.builder()
                .name(savedPhoneIdentityCertificationHistory.getName())
                .birthday(savedPhoneIdentityCertificationHistory.getBirthday())
                .gender(savedPhoneIdentityCertificationHistory.getGender())
                .mobileCarrier(savedPhoneIdentityCertificationHistory.getMobileCarrier())
                .phoneNumber(savedPhoneIdentityCertificationHistory.getPhoneNumber())
                .build());

        // then
        assertThat(certificationKeyId).isEqualTo(certificationKey.getId());
    }

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 인증")
    void certifyOtpNumber() {
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        PhoneIdentityCertificationHistory savedPhoneIdentityCertificationHistory = PhoneIdentityCertificationHistoryMock.createHistory(certificationKey);
        given(phoneIdentityCertificationHistoryRepository.findByCertificationKeyAndPhoneNumber(any(CertificationKey.class), anyString()))
            .willReturn(Optional.of(savedPhoneIdentityCertificationHistory));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        String phoneNumber = "01011112222";
        String otpNumber = "000000";
        phoneIdentityCertificationDomainService.certifyOtpNumber(certificationKey, phoneNumber, otpNumber);

        assertThat(savedPhoneIdentityCertificationHistory.getCi()).isNotNull();
        assertThat(savedPhoneIdentityCertificationHistory.getDi()).isNotNull();
        verify(phoneIdentityCertificationHistoryRepository).findByCertificationKeyAndPhoneNumber(certificationKey, phoneNumber);
        verify(passwordEncoder).matches(otpNumber, savedPhoneIdentityCertificationHistory.getOtpNumber());
    }

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 인증 - 존재하지 않는 인증내역")
    void certifyOtpNumber_notExistCertificationHistory() {
        // given
        given(phoneIdentityCertificationHistoryRepository.findByCertificationKeyAndPhoneNumber(any(CertificationKey.class), anyString()))
            .willReturn(Optional.empty());

        // when
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        String phoneNumber = "01011112222";
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> phoneIdentityCertificationDomainService.certifyOtpNumber(certificationKey, phoneNumber, "000000"));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY.getMessage());
        verify(phoneIdentityCertificationHistoryRepository).findByCertificationKeyAndPhoneNumber(certificationKey, phoneNumber);
    }

    @Test
    @DisplayName("휴대폰 본인인증 OTP 번호 인증 - OPT 번호 불일치")
    void certifyOtpNumber_incorrectOTPNumber() {
        // given
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        PhoneIdentityCertificationHistory savedPhoneIdentityCertificationHistory = PhoneIdentityCertificationHistoryMock.createHistory(certificationKey);
        given(phoneIdentityCertificationHistoryRepository.findByCertificationKeyAndPhoneNumber(any(CertificationKey.class), anyString()))
            .willReturn(Optional.of(savedPhoneIdentityCertificationHistory));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        String phoneNumber = "01011112222";
        String optNumber = "000000";
        Throwable exception = assertThrows(InvalidDataException.class,
            () -> phoneIdentityCertificationDomainService.certifyOtpNumber(certificationKey, phoneNumber, optNumber));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.INVALID_OTP_NUMBER.getMessage());
        verify(phoneIdentityCertificationHistoryRepository).findByCertificationKeyAndPhoneNumber(certificationKey, phoneNumber);
        verify(passwordEncoder).matches(optNumber, savedPhoneIdentityCertificationHistory.getOtpNumber());
    }

    @Test
    @DisplayName("휴대폰 본인인증 정보 조회")
    void getCertificationInfo() {
        // given
        CertificationKey certificationKey = CertificationKeyMock.createCertifiedKey(UUID.randomUUID());
        PhoneIdentityCertificationHistory savedPhoneIdentityCertificationHistory = PhoneIdentityCertificationHistoryMock.createHistory(certificationKey);
        given(phoneIdentityCertificationHistoryRepository.findByCertificationKey(any(CertificationKey.class)))
            .willReturn(Optional.of(savedPhoneIdentityCertificationHistory));

        // when
        CertificationInfoDTO certificationInfo = phoneIdentityCertificationDomainService.getCertificationInfo(certificationKey);

        // then
        assertThat(certificationInfo.getName()).isEqualTo(savedPhoneIdentityCertificationHistory.getName());
        assertThat(certificationInfo.getPhoneNumber()).isEqualTo(savedPhoneIdentityCertificationHistory.getPhoneNumber());
        assertThat(certificationInfo.getCi()).isEqualTo(savedPhoneIdentityCertificationHistory.getCi());
        assertThat(certificationInfo.getDi()).isEqualTo(savedPhoneIdentityCertificationHistory.getDi());
        assertThat(certificationInfo.getBirthday()).isEqualTo(savedPhoneIdentityCertificationHistory.getBirthday());
        assertThat(certificationInfo.getGender()).isEqualTo(savedPhoneIdentityCertificationHistory.getGender());
        verify(phoneIdentityCertificationHistoryRepository).findByCertificationKey(certificationKey);
    }

    @Test
    @DisplayName("휴대폰 본인인증 정보 조회 - 존재하지 않는 인증 정보")
    void getCertificationInfo_notExistCertificationInfo() {
        // given
        given(phoneIdentityCertificationHistoryRepository.findByCertificationKey(any(CertificationKey.class)))
            .willReturn(Optional.empty());

        // when
        CertificationKey certificationKey = CertificationKeyMock.createCertifiedKey(UUID.randomUUID());
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> phoneIdentityCertificationDomainService.getCertificationInfo(certificationKey));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY.getMessage());
        verify(phoneIdentityCertificationHistoryRepository).findByCertificationKey(certificationKey);
    }

}