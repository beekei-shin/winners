package com.winners.appApi.application.shop.dto;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.config.querydsl.QuerydslSelectDTO;
import org.winners.core.domain.shop.ShopType;

import java.util.List;

import static org.winners.core.domain.shop.QShop.shop;
import static org.winners.core.domain.shop.QShopCategory.shopCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopListDTO implements QuerydslSelectDTO {

    private long shopId;
    private ShopType shopType;
    private String shopName;
    private String categoryNames;
    private String businessNumber;
    private List<String> telNumber;
    private String zipCode;
    private String address;
    private String detailAddress;

    @Override
    public ConstructorExpression<ShopListDTO> constructor() {
        return Projections.constructor(
            ShopListDTO.class,
            shop.id,
            shop.type,
            shop.name,
            Expressions.stringTemplate("GROUP_CONCAT({0}, ' ')", shopCategory.name),
            shop.businessNumber,
            shop.telNumber,
            shop.address.zipCode,
            shop.address.address,
            shop.address.detailAddress.nullif(""));
    }

}
