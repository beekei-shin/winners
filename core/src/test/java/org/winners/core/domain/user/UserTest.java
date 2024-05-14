package org.winners.core.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotAccessDataException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    @DisplayName("회원 차단여부 조회")
    void isBlockUser() {
        ClientUser user = ClientUserMock.createUser();
        assertThat(user.isBlockUser()).isEqualTo(false);

        ClientUser blockUser = ClientUserMock.createBlockUser();
        assertThat(blockUser.isBlockUser()).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 탈퇴여부 조회")
    void isResignUser() {
        ClientUser user = ClientUserMock.createUser();
        assertThat(user.isResignUser()).isEqualTo(false);

        ClientUser resignUser = ClientUserMock.createResignUser();
        assertThat(resignUser.isResignUser()).isEqualTo(true);
    }

    @Test
    @DisplayName("허용된 회원 확인")
    void accessUserCheck() {
        ClientUser user = ClientUserMock.createUser();
        user.accessUserCheck();
        assertThat(user.isBlockUser()).isFalse();
        assertThat(user.isResignUser()).isFalse();

        ClientUser blockUser = ClientUserMock.createBlockUser();
        Throwable blockException = assertThrows(NotAccessDataException.class, blockUser::accessUserCheck);
        assertThat(blockException.getMessage()).isEqualTo(ExceptionMessageType.BLOCK_USER.getMessage());

        ClientUser resignUser = ClientUserMock.createResignUser();
        Throwable resignException = assertThrows(NotAccessDataException.class, resignUser::accessUserCheck);
        assertThat(resignException.getMessage()).isEqualTo(ExceptionMessageType.RESIGN_USER.getMessage());
    }

}