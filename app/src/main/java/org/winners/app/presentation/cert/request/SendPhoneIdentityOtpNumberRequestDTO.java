package org.winners.app.presentation.cert.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.winners.core.domain.base.Gender;
import org.winners.core.domain.cert.MobileCarrier;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendPhoneIdentityOtpNumberRequestDTO {

    @NotNull
    private MobileCarrier mobileCarrier;

    @NotBlank
    @Length(max = 50)
    private String name;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private Gender gender;

    @NotBlank
    @Length(max = 12)
    private String phoneNumber;

}
