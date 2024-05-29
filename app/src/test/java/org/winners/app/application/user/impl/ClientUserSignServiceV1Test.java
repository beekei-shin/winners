package org.winners.app.application.user.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.winners.app.application.ApplicationServiceTest;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;
import org.winners.core.config.exception.*;
import org.winners.core.domain.auth.service.AuthDomainService;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationKeyMock;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistory;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistoryMock;
import org.winners.core.domain.cert.service.CertificationKeyDomainService;
import org.winners.core.domain.cert.service.PhoneIdentityCertificationDomainService;
import org.winners.core.domain.cert.service.dto.CertificationInfoDTO;
import org.winners.core.domain.common.DeviceOs;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserMock;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.ClientUserDomainService;
import org.winners.core.domain.user.service.dto.SaveClientUserParameterDTO;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

class ClientUserSignServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private ClientUserSignServiceV1 clientUserSignServiceV1;

    @Mock
    private ClientUserRepository clientUserRepository;

    @Mock
    private ClientUserDomainService clientUserDomainService;

    @Mock
    private CertificationKeyDomainService certificationKeyDomainService;

    @Mock
    private PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;

    @Mock
    private AuthDomainService authDomainService;

    @Test
    @DisplayName("사용자 회원 가입")
    void signUp() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        willDoNothing().given(clientUserDomainService).duplicatePhoneNumberCheck(anyString());
        willDoNothing().given(clientUserDomainService).duplicateCiCheck(anyString());

        ClientUser clientUser = ClientUserMock.createUser();
        given(clientUserDomainService.saveClientUser(any(SaveClientUserParameterDTO.class))).willReturn(clientUser);

        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        given(authDomainService.createClientUserAuthToken(anyLong(), any(DeviceOs.class), anyString())).willReturn(authToken);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        SignUpClientUserResultDTO result = clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken);

        // then
        assertThat(result.getUserId()).isEqualTo(clientUser.getId());
        assertThat(result.getUserName()).isEqualTo(clientUser.getName());
        assertThat(result.getPhoneNumber()).isEqualTo(clientUser.getPhoneNumber());
        assertThat(result.getAccessToken()).isEqualTo(authToken.getAccessToken());
        assertThat(result.getRefreshToken()).isEqualTo(authToken.getRefreshToken());
        assertThat(certifiedCertificationKey.isUsed()).isEqualTo(true);

        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserDomainService).duplicatePhoneNumberCheck(certificationInfo.getPhoneNumber());
        verify(clientUserDomainService).duplicateCiCheck(certificationInfo.getCi());
        verify(authDomainService).createClientUserAuthToken(clientUser.getId(), deviceOs, deviceToken);
    }

    @Test
    @DisplayName("사용자 회원 가입 - 존재하지 않는 인증키")
    void signUp_notExistCertificationKey() {
        // given
        willThrow(NotExistDataException.class).given(certificationKeyDomainService).getSavedCertificationKey(any(UUID.class));

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(NotExistDataException.class, () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 가입 - 만료된 인증키")
    void signUp_expiredCertificationKey() {
        // given
        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(expiredCertificationKey);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 가입 - 인증되지 않은 인증키")
    void signUp_notCertifiedKey() {
        // given
        CertificationKey certificationKey = CertificationKeyMock.createKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certificationKey);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(UnprocessedDataException.class,
            () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_CERTIFIED_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 가입 - 사용한 인증키")
    void signUp_usedCertificationKey() {
        // given
        CertificationKey usedCertificationKey = CertificationKeyMock.createCertifiedAndUsedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(usedCertificationKey);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 가입 - 존재하지 않는 인증 정보")
    void signUp_notExistCertificationInfo() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);
        willThrow(NotExistDataException.class).given(phoneIdentityCertificationDomainService).getCertificationInfo(any(CertificationKey.class));

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(NotExistDataException.class,
            () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
    }

    @Test
    @DisplayName("사용자 회원 가입 - 중복된 휴대폰번호")
    void signUp_duplicatedPhoneNumber() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        willThrow(DuplicatedDataException.class).given(clientUserDomainService).duplicatePhoneNumberCheck(anyString());

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(DuplicatedDataException.class,
            () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserDomainService).duplicatePhoneNumberCheck(certificationInfo.getPhoneNumber());
    }

    @Test
    @DisplayName("사용자 회원 가입 - 중복된 CI")
    void signUp_duplicatedCi() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        willDoNothing().given(clientUserDomainService).duplicatePhoneNumberCheck(anyString());
        willThrow(DuplicatedDataException.class).given(clientUserDomainService).duplicateCiCheck(anyString());

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(DuplicatedDataException.class,
            () -> clientUserSignServiceV1.signUp(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserDomainService).duplicatePhoneNumberCheck(certificationInfo.getPhoneNumber());
        verify(clientUserDomainService).duplicateCiCheck(certificationInfo.getCi());
    }

    @Test
    @DisplayName("사용자 회원 로그인")
    void signIn() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        ClientUser clientUser = ClientUserMock.createUser();
        given(clientUserRepository.findByPhoneNumberAndCi(anyString(), anyString())).willReturn(Optional.of(clientUser));

        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        given(authDomainService.createClientUserAuthToken(anyLong(), any(DeviceOs.class), anyString())).willReturn(authToken);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        SignInClientUserResultDTO result = clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken);

        // then
        assertThat(result.getUserId()).isEqualTo(clientUser.getId());
        assertThat(result.getUserName()).isEqualTo(clientUser.getName());
        assertThat(result.getPhoneNumber()).isEqualTo(clientUser.getPhoneNumber());
        assertThat(result.getAccessToken()).isEqualTo(authToken.getAccessToken());
        assertThat(result.getRefreshToken()).isEqualTo(authToken.getRefreshToken());
        assertThat(certifiedCertificationKey.isUsed()).isEqualTo(true);

        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserRepository).findByPhoneNumberAndCi(certifiedHistory.getPhoneNumber(), certifiedHistory.getCi());
        verify(authDomainService).createClientUserAuthToken(clientUser.getId(), deviceOs, deviceToken);
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 존재하지 않는 인증키")
    void signIn_notExistCertificationKey() {
        willThrow(NotExistDataException.class).given(certificationKeyDomainService).getSavedCertificationKey(any(UUID.class));

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(NotExistDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 만료된 인증키")
    void signIn_expiredCertificationKey() {
        // given
        CertificationKey expiredCertificationKey = CertificationKeyMock.createExpiredKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(expiredCertificationKey);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.EXPIRED_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 인증되지 않은 인증키")
    void signIn_notCertifiedKey() {
        // given
        CertificationKey certificationKey = CertificationKeyMock.createKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certificationKey);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(UnprocessedDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_CERTIFIED_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 사용한 인증키")
    void signIn_usedCertificationKey() {
        // given
        CertificationKey usedCertificationKey = CertificationKeyMock.createCertifiedAndUsedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(usedCertificationKey);

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(AlreadyProcessedDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.ALREADY_USED_CERTIFICATION_KEY.getMessage());
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 존재하지 않는 인증 정보")
    void signIn_notExistCertificationInfo() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);
        willThrow(NotExistDataException.class).given(phoneIdentityCertificationDomainService).getCertificationInfo(any(CertificationKey.class));

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(NotExistDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 존재하지 않는 회원")
    void signIn_notExistClientUser() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        given(clientUserRepository.findByPhoneNumberAndCi(anyString(), anyString())).willReturn(Optional.empty());

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_USER.getMessage());

        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserRepository).findByPhoneNumberAndCi(certifiedHistory.getPhoneNumber(), certifiedHistory.getCi());
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 차단 회원")
    void signIn_blockUser() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        ClientUser clientUser = ClientUserMock.createBlockUser();
        given(clientUserRepository.findByPhoneNumberAndCi(anyString(), anyString())).willReturn(Optional.of(clientUser));

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(NotAccessDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserRepository).findByPhoneNumberAndCi(certifiedHistory.getPhoneNumber(), certifiedHistory.getCi());
    }

    @Test
    @DisplayName("사용자 회원 로그인 - 탈퇴 회원")
    void signIn_resignUser() {
        // given
        CertificationKey certifiedCertificationKey = CertificationKeyMock.createCertifiedKey();
        given(certificationKeyDomainService.getSavedCertificationKey(any(UUID.class))).willReturn(certifiedCertificationKey);

        PhoneIdentityCertificationHistory certifiedHistory = PhoneIdentityCertificationHistoryMock.createCertifiedHistory(certifiedCertificationKey);
        CertificationInfoDTO certificationInfo = CertificationInfoDTO.convert(certifiedHistory);
        given(phoneIdentityCertificationDomainService.getCertificationInfo(any(CertificationKey.class))).willReturn(certificationInfo);

        ClientUser clientUser = ClientUserMock.createResignUser();
        given(clientUserRepository.findByPhoneNumberAndCi(anyString(), anyString())).willReturn(Optional.of(clientUser));

        // when
        UUID certificationKeyId = UUID.randomUUID();
        DeviceOs deviceOs = DeviceOs.AOS;
        String deviceToken = "deviceToken";
        assertThrows(NotAccessDataException.class,
            () -> clientUserSignServiceV1.signIn(certificationKeyId, deviceOs, deviceToken));

        // then
        verify(certificationKeyDomainService).getSavedCertificationKey(certificationKeyId);
        verify(phoneIdentityCertificationDomainService).getCertificationInfo(certifiedCertificationKey);
        verify(clientUserRepository).findByPhoneNumberAndCi(certifiedHistory.getPhoneNumber(), certifiedHistory.getCi());
    }

}