package org.winners.backoffice.application.shop.dto;

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
public class ShopInfoDTO implements QuerydslSelectDTO {

    private long shopId;
    private ShopType shopType;
    private ShopStatus shopStatus;
    private String shopName;
    private String businessNumber;
    private String zipCode;
    private String address;
    private String detailAddress;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    @Override
    public ConstructorExpression constructor() {
        return Projections.constructor(
            ShopInfoDTO.class,
            shop.id,
            shop.type,
            shop.status,
            shop.name,
            shop.businessNumber,
            shop.address.zipCode,
            shop.address.address,
            shop.address.detailAddress,
            shop.createdDatetime,
            shop.updatedDatetime
        );
    }

}
