package org.winners.core.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessUserTest {

    @Test
    @DisplayName("사업자 회원 생성")
    void createUser() {
        String name = "홍길동";
        String phoneNumber = "01012341234";
        String password = "password";
        BusinessUser businessUser = BusinessUser.createUser(name, phoneNumber, password);

        assertThat(businessUser.getName()).isEqualTo(name);
        assertThat(businessUser.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(businessUser.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void updatePassword() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);

        String password = "newPassword";
        businessUser.updatePassword(password);

        assertThat(businessUser.getPassword()).isEqualTo(password);
    }

}