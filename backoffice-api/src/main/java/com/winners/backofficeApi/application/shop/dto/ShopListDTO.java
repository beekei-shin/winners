package com.winners.backofficeApi.application.shop.dto;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.config.querydsl.QuerydslSelectDTO;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;

import java.time.LocalDateTime;

import static org.winners.core.domain.shop.QShop.shop;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopListDTO implements QuerydslSelectDTO {

    private long shopId;
    private ShopType shopType;
    private ShopStatus shopStatus;
    private String shopName;
    private String businessNumber;
    private LocalDateTime createdDatetime;

    @Override
    public ConstructorExpression<ShopListDTO> constructor() {
        return Projections.constructor(
            ShopListDTO.class,
            shop.id,
            shop.type,
            shop.status,
            shop.name,
            shop.businessNumber,
            shop.createdDatetime
        );
    }

}
