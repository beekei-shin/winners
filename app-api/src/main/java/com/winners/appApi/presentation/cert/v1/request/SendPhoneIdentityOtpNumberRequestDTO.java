package com.winners.appApi.presentation.cert.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.winners.core.config.presentation.validation.ValidEnum;
import org.winners.core.domain.cert.MobileCarrier;
import org.winners.core.domain.common.Gender;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendPhoneIdentityOtpNumberRequestDTO {

    @NotBlank
    @ValidEnum(enumClass = MobileCarrier.class)
    private String mobileCarrier;

    @NotBlank
    @Length(max = 50)
    private String userName;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    @ValidEnum(enumClass = Gender.class)
    private String gender;

    @NotBlank
    @Length(max = 12)
    private String phoneNumber;

    public MobileCarrier getMobileCarrier() {
        return MobileCarrier.valueOf(this.mobileCarrier);
    }

    public Gender getGender() {
        return Gender.valueOf(this.gender);
    }

}
