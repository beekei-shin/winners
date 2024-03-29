package org.winners.core.domain.cert.service.dto;

import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.base.Gender;
import org.winners.core.domain.cert.MobileCarrier;

import java.time.LocalDate;

@Getter
@Builder
public class SendOtpNumberParameterDTO {
    private final String name;
    private final LocalDate birthday;
    private final Gender gender;
    private final MobileCarrier mobileCarrier;
    private final String phoneNumber;
}
