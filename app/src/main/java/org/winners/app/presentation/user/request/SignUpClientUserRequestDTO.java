package org.winners.app.presentation.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpClientUserRequestDTO {

    @NotNull
    private UUID authenticationKey;

}
