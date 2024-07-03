package org.winners.backoffice.application.shop.dto;

import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;

@Getter
@Builder
public class GetShopListSearchParameterDTO {
    private final ShopType shopType;
    private final ShopStatus shopStatus;
    private final String keyword;
}
