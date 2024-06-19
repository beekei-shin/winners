package org.winners.core.domain.shop;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopMock {

    public static Shop createShop(long id) {
        return createShop(id, null);
    }

    public static Shop createShop(long id, Set<Long> categoryIds) {
        Shop shop = Shop.builder()
            .id(id)
            .type(ShopType.RESTAURANT)
            .status(ShopStatus.OPEN)
            .name("테스트 상점")
            .businessNumber("1234567890")
            .build();
        shop.saveAndUpdateCategories(categoryIds);
        return shop;
    }

}
