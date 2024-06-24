package org.winners.core.domain.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserMock;
import org.winners.core.domain.user.BusinessUserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BusinessUserDomainServiceTest extends DomainServiceTest {

    private BusinessUserDomainService businessUserDomainService;
    private BusinessUserRepository businessUserRepository;

    @BeforeEach
    public void BeforeEach() {
        this.businessUserRepository = Mockito.mock(BusinessUserRepository.class);
        this.businessUserDomainService = new BusinessUserDomainService(this.businessUserRepository);
    }

    @Test
    @DisplayName("사업자 회원 중복 확인")
    void duplicateBusinessUserCheck() {
        given(businessUserRepository.countByPhoneNumber(anyString())).willReturn(0L);

        String phoneNumber = "01012341234";
        businessUserDomainService.duplicateBusinessUserCheck(phoneNumber);

        verify(businessUserRepository).countByPhoneNumber(phoneNumber);
    }

    @Test
    @DisplayName("사업자 회원 중복 확인 - 중복된 사업자 회원")
    void duplicateBusinessUserCheck_duplicatedBusinessUser() {
        given(businessUserRepository.countByPhoneNumber(anyString())).willReturn(1L);

        String phoneNumber = "01012341234";

        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> businessUserDomainService.duplicateBusinessUserCheck(phoneNumber));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_PHONE_NUMBER.getMessage());
        verify(businessUserRepository).countByPhoneNumber(phoneNumber);
    }

    @Test
    @DisplayName("사용자 회원 조회")
    void getBusinessUser() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserRepository.findById(anyLong())).willReturn(Optional.of(businessUser));

        long userId = 1;
        BusinessUser returnBusinessUser = businessUserDomainService.getBusinessUser(userId);

        assertThat(returnBusinessUser).isEqualTo(businessUser);
        verify(businessUserRepository).findById(userId);
    }

    @Test
    @DisplayName("사용자 회원 조회 - 존재하지 않는 회원")
    void getBusinessUser_notExistUser() {
        given(businessUserRepository.findById(anyLong())).willReturn(Optional.empty());

        long userId = 1;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> businessUserDomainService.getBusinessUser(userId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_USER.getMessage());
        verify(businessUserRepository).findById(userId);
    }

    @Test
    @DisplayName("휴대폰 번호 별 사용자 회원 조회")
    void getBusinessUserByPhoneNumber() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        given(businessUserRepository.findByPhoneNumber(anyString())).willReturn(Optional.of(businessUser));

        String phoneNumber = "01012341234";
        BusinessUser returnBusinessUser = businessUserDomainService.getBusinessUserByPhoneNumber(phoneNumber);

        assertThat(returnBusinessUser).isEqualTo(businessUser);
        verify(businessUserRepository).findByPhoneNumber(phoneNumber);
    }

    @Test
    @DisplayName("휴대폰 번호 별 사용자 회원 조회 - 존재하지 않는 회원")
    void getBusinessUserByPhoneNumber_notExistUser() {
        given(businessUserRepository.findByPhoneNumber(anyString())).willReturn(Optional.empty());

        String phoneNumber = "01012341234";
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> businessUserDomainService.getBusinessUserByPhoneNumber(phoneNumber));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_USER.getMessage());
        verify(businessUserRepository).findByPhoneNumber(phoneNumber);
    }

}