package org.winners.core.domain.shop;

import java.util.Optional;

public interface ShopRepository {
    Shop saveAndFlush(Shop shop);
    long countByTypeAndBusinessNumber(ShopType type, String businessNumber);
    long countByTypeAndBusinessNumberAndIdNot(ShopType type, String businessNumber, long id);
    Optional<Shop> findById(long shopId);
}
