package org.winners.core.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClientUserTest {

    @Test
    @DisplayName("사용자 회원 생성")
    void create() {
        String name = "홍길동";
        String phoneNumber = "01011112222";
        String ci = "test_ci";
        String di = "test_di";
        LocalDate birthday = LocalDate.of(1993, 10, 20);
        Gender gender = Gender.MALE;
        ClientUser clientUser = ClientUser.create(
            name, phoneNumber,
            ci, di,
            birthday,gender);

        assertThat(clientUser.getType()).isEqualTo(UserType.CLIENT);
        assertThat(clientUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(clientUser.getName()).isEqualTo(name);
        assertThat(clientUser.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(clientUser.getCi()).isEqualTo(ci);
        assertThat(clientUser.getDi()).isEqualTo(di);
        assertThat(clientUser.getBirthday()).isEqualTo(birthday);
        assertThat(clientUser.getGender()).isEqualTo(gender);
    }

    @Test
    @DisplayName("사용자 회원 차단")
    void block() {
        ClientUser clientUser = ClientUserMock.createUser();
        clientUser.block();

        assertThat(clientUser.isBlockUser()).isEqualTo(true);
        assertThat(clientUser.getStatus()).isEqualTo(UserStatus.BLOCK);
    }

    @Test
    @DisplayName("사용자 회원 탈퇴")
    void resign() {
        ClientUser clientUser = ClientUserMock.createUser();
        clientUser.resign();

        assertThat(clientUser.isResignUser()).isEqualTo(true);
        assertThat(clientUser.getStatus()).isEqualTo(UserStatus.RESIGN);
    }

}