package com.winners.backofficeApi.application.shop.dto;

import com.querydsl.core.types.OrderSpecifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.winners.core.config.presentation.OrderByParameterDTO;

import java.util.List;

import static org.winners.core.domain.shop.QShop.shop;

@Getter
@AllArgsConstructor
public enum GetShopListOrderByParameterDTO implements OrderByParameterDTO {

    CREATE_DATE_ASC(List.of(shop.createdDatetime.asc(), shop.id.desc())),
    CREATE_DATE_DESC(List.of(shop.createdDatetime.desc(), shop.id.desc())),
    SHOP_NAME_ASC(List.of(shop.name.asc(), shop.id.desc())),
    SHOP_NAME_DESC(List.of(shop.name.desc(), shop.id.desc())),
    BUSINESS_NUMBER_ASC(List.of(shop.businessNumber.asc(), shop.id.desc())),
    BUSINESS_NUMBER_DESC(List.of(shop.businessNumber.desc(), shop.id.desc())),
    ;

    private final List<OrderSpecifier<?>> specifiers;

}
