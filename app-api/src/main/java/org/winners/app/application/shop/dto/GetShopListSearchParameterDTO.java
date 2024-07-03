package org.winners.app.application.shop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class GetShopListSearchParameterDTO {
    private final Set<Long> categoryIds;
    private final String keyword;
}
