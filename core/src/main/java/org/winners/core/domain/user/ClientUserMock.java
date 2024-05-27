package org.winners.core.domain.user;

import org.winners.core.domain.common.Gender;

import java.time.LocalDate;

public class ClientUserMock {

    public static ClientUser createUser() {
        ClientUser clientUser = ClientUser.create("홍길동", "01011112222", "ci", "di", LocalDate.of(1993, 10, 20), Gender.MALE);
        clientUser.setMockId(1);
        return clientUser;
    }

    public static ClientUser createBlockUser() {
        ClientUser clientUser = createUser();
        clientUser.block();
        return clientUser;
    }

    public static ClientUser createResignUser() {
        ClientUser clientUser = createUser();
        clientUser.resign();
        return clientUser;
    }

}
