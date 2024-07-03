package com.winners.backofficeApi.presentation.shop.v1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DisconnectShopToBusinessUserRequestDTO {

    @NotNull
    @Min(value = 1)
    private Long shopId;

    @NotNull
    @Min(value = 1)
    private Long userId;

}
