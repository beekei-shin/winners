package org.winners.backoffice.application.shop.dto;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.winners.core.config.querydsl.QuerydslSelectDTO;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;

import java.time.LocalDateTime;
import java.util.List;

import static org.winners.core.domain.shop.QShop.shop;
import static org.winners.core.domain.user.QBusinessUser.businessUser;

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
    private List<ShopUserInfoDTO> userList;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    @Getter
    @AllArgsConstructor
    public static class ShopUserInfoDTO {
        private long userId;
        private String userName;
        private String phoneNumber;
    }

    @Override
    public ConstructorExpression<ShopInfoDTO> constructor() {
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
            GroupBy.list(Projections.constructor(ShopUserInfoDTO.class, businessUser.id, businessUser.name, businessUser.phoneNumber)),
            shop.createdDatetime,
            shop.updatedDatetime
        );
    }

}
