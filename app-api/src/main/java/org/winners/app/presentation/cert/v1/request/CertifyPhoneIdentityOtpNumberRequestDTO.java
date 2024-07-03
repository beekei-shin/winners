package org.winners.app.presentation.cert.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CertifyPhoneIdentityOtpNumberRequestDTO {

    @NotNull
    private UUID certificationKey;

    @NotBlank
    @Length(max = 12)
    private String phoneNumber;

    @NotBlank
    @Length(max = 6)
    private String optNumber;

}
