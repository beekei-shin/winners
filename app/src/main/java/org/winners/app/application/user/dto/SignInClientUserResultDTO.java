package org.winners.app.application.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignInClientUserResultDTO {

    private final boolean successSignIn;
    private final boolean notExistUser;
    private final boolean blockUser;

    public static SignInClientUserResultDTO successSignIn() {
        return SignInClientUserResultDTO.builder().successSignIn(true).build();
    }

    public static SignInClientUserResultDTO notExistUser() {
        return SignInClientUserResultDTO.builder()
            .successSignIn(false)
            .notExistUser(true)
            .blockUser(false)
            .build();
    }

    public static SignInClientUserResultDTO blockUser() {
        return SignInClientUserResultDTO.builder()
            .successSignIn(false)
            .notExistUser(false)
            .blockUser(true)
            .build();
    }

}
