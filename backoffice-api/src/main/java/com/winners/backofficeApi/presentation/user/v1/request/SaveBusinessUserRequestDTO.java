package com.winners.backofficeApi.presentation.user.v1.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveBusinessUserRequestDTO {

    @NotBlank
    @Length(max = 50)
    private String userName;

    @NotBlank
    @Length(max = 12)
    private String phoneNumber;

    @NotBlank
    @Length(max = 30)
    private String password;

}
