package org.winners.backoffice.presentation.shop.v1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectShopToBusinessUserRequestDTO {

    @NotNull
    @Min(value = 1)
    private Long shopId;

    @NotNull
    @Min(value = 1)
    private Long userId;

}
