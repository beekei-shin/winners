package org.winners.core.domain.user;

import org.winners.core.domain.common.Gender;

import java.time.LocalDate;

public class ClientUserMock {

    public static ClientUser createUser(long id) {
        ClientUser clientUser = ClientUser.createUser("홍길동", "01011112222", "ci", "di", LocalDate.of(1993, 10, 20), Gender.MALE);
        clientUser.id = id;
        return clientUser;
    }

    public static ClientUser createBlockUser(long id) {
        ClientUser clientUser = createUser(id);
        clientUser.block();
        return clientUser;
    }

    public static ClientUser createResignUser(long id) {
        ClientUser clientUser = createUser(id);
        clientUser.resign();
        return clientUser;
    }

}
