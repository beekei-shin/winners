package org.winners.app.application.user.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;

@Getter
@Builder
public class SignUpClientUserParameterDTO {

    private final String userName;

    private final String phoneNumber;

    private final String ci;

    @Nullable
    private final String di;

    private LocalDate userBirthday;

    private Gender userGender;

}
