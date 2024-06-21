package org.winners.core.domain.shop;

import java.util.Set;

public class ShopMock {

    public static Shop createShop(long id) {
        return createShopWithCategory(id, null);
    }

    public static Shop createShopWithCategory(long id, Set<Long> categoryIds) {
        Shop shop = Shop.builder()
            .id(id)
            .type(ShopType.RESTAURANT)
            .status(ShopStatus.OPEN)
            .name("테스트 상점")
            .businessNumber("1234567890")
            .build();
        shop.connectCategories(categoryIds);
        return shop;
    }

    public static Shop createShopWithUser(long id, Set<Long> userIds) {
        Shop shop = Shop.builder()
            .id(id)
            .type(ShopType.RESTAURANT)
            .status(ShopStatus.OPEN)
            .name("테스트 상점")
            .businessNumber("1234567890")
            .build();
        userIds.forEach(shop::connectUser);
        return shop;
    }

}
