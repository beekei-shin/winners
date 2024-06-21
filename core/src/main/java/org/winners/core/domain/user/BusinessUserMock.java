package org.winners.core.domain.user;

public class BusinessUserMock {

    public static BusinessUser createUser(long id) {
        BusinessUser businessUser = BusinessUser.createUser("홍길동", "01012341234", "password486");
        businessUser.id = id;
        return businessUser;
    }

}
