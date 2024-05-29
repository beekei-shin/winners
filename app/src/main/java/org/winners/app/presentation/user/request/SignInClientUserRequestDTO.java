package org.winners.app.presentation.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.winners.core.config.presentation.validation.ValidEnum;
import org.winners.core.domain.common.DeviceOs;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInClientUserRequestDTO {

    @NotNull
    private UUID certificationKey;

    @NotBlank
    @ValidEnum(enumClass = DeviceOs.class)
    private String deviceOs;

    @NotBlank
    @Length(max = 500)
    private String deviceToken;

    public DeviceOs getDeviceOs() {
        return DeviceOs.valueOf(this.deviceOs);
    }

}
