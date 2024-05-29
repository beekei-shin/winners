package org.winners.app.presentation.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.config.presentation.validation.ValidEnum;
import org.winners.core.domain.common.DeviceOs;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInClientUserRequestDTO {

    @NotNull
    private UUID certificationKey;

    @NotNull
    @ValidEnum(enumClass = DeviceOs.class)
    private String deviceOs;

    @NotBlank
    private String deviceToken;

    public DeviceOs getDeviceOs() {
        return DeviceOs.valueOf(this.deviceOs);
    }

}
