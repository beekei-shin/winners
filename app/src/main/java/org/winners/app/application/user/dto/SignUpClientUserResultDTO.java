package org.winners.app.application.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SignUpClientUserResultDTO {

    private final boolean successSignUp;
    private final boolean duplicatedPhoneNumber;
    private final boolean duplicatedCi;

    public static SignUpClientUserResultDTO successSignIn() {
        return SignUpClientUserResultDTO.builder()
            .successSignUp(true)
            .duplicatedPhoneNumber(false)
            .duplicatedCi(false)
            .build();
    }

    public static SignUpClientUserResultDTO duplicatedPhoneNumber() {
        return SignUpClientUserResultDTO.builder()
            .successSignUp(false)
            .duplicatedPhoneNumber(true)
            .duplicatedCi(false)
            .build();
    }

    public static SignUpClientUserResultDTO duplicatedCi() {
        return SignUpClientUserResultDTO.builder()
            .successSignUp(false)
            .duplicatedPhoneNumber(false)
            .duplicatedCi(true)
            .build();
    }

}
