package org.winners.core.domain.user.service.dto;

import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.common.Gender;

import java.time.LocalDate;

@Getter
@Builder
public class SaveClientUserParameterDTO {
    private final String name;
    private final String phoneNumber;
    private final String ci;
    private final String di;
    private final LocalDate birthday;
    private final Gender gender;
}
