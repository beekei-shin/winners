package org.winners.core.domain.user.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.DomainServiceTest;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserMock;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.dto.SaveClientUserParameterDTO;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ClientUserDomainServiceTest extends DomainServiceTest {

    private ClientUserDomainService clientUserDomainService;
    private ClientUserRepository clientUserRepository;

    @BeforeAll
    public void beforeAll() {
        this.clientUserRepository = Mockito.mock(ClientUserRepository.class);
        this.clientUserDomainService = new ClientUserDomainService(this.clientUserRepository);
    }

    @Test
    @DisplayName("휴대폰 번호 중복 확인")
    void duplicatePhoneNumberCheck() {
        String phoneNumber1 = "01011111111";
        given(clientUserRepository.countByPhoneNumber(anyString())).willReturn(0L);
        clientUserDomainService.duplicatePhoneNumberCheck(phoneNumber1);
        verify(clientUserRepository).countByPhoneNumber(phoneNumber1);

        String phoneNumber2 = "01022222222";
        given(clientUserRepository.countByPhoneNumber(anyString())).willReturn(1L);
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> clientUserDomainService.duplicatePhoneNumberCheck(phoneNumber2));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_PHONE_NUMBER.getMessage());
        verify(clientUserRepository).countByPhoneNumber(phoneNumber2);
    }

    @Test
    @DisplayName("CI 중복 확인")
    void duplicateCICheck() {
        String ci1 = "ci1";
        given(clientUserRepository.countByCi(anyString())).willReturn(0L);
        clientUserDomainService.duplicateCiCheck(ci1);
        verify(clientUserRepository).countByCi(ci1);

        String ci2 = "ci2";
        given(clientUserRepository.countByCi(anyString())).willReturn(1L);
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> clientUserDomainService.duplicateCiCheck(ci2));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_CI.getMessage());
        verify(clientUserRepository).countByCi(ci2);
    }

    @Test
    @DisplayName("사용자 회원 등록")
    void saveClientUser() {
        ClientUser savedClientUser = ClientUserMock.createUser(1L);
        given(clientUserRepository.save(any(ClientUser.class))).willReturn(savedClientUser);

        SaveClientUserParameterDTO saveClientUserParameter = SaveClientUserParameterDTO.builder()
            .name(savedClientUser.getName())
            .phoneNumber(savedClientUser.getPhoneNumber())
            .ci(savedClientUser.getCi())
            .di(savedClientUser.getDi())
            .birthday(savedClientUser.getBirthday())
            .gender(savedClientUser.getGender())
            .build();
        ClientUser returnClientUser = clientUserDomainService.saveClientUser(saveClientUserParameter);

        assertThat(returnClientUser).isEqualTo(savedClientUser);
    }

    @Test
    @DisplayName("사용자 회원 조회")
    void getClientUser() {
        ClientUser savedClientUser = ClientUserMock.createUser(1L);
        given(clientUserRepository.findById(anyLong())).willReturn(Optional.of(savedClientUser));

        ClientUser returnClientUser = clientUserDomainService.getClientUser(1L);

        assertThat(returnClientUser).isEqualTo(savedClientUser);
    }

    @Test
    @DisplayName("사용자 회원 조회 - 존재하지 않는 회원")
    void getClientUser_notExistUser() {
        given(clientUserRepository.findById(anyLong())).willReturn(Optional.empty());

        Throwable exception = assertThrows(NotExistDataException.class,
            () -> clientUserDomainService.getClientUser(1L));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_USER.getMessage());
        verify(clientUserRepository).findById(1L);
    }

}