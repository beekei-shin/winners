package org.winners.core.domain.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.shop.Shop;
import org.winners.core.domain.shop.ShopRepository;
import org.winners.core.domain.shop.ShopType;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopDomainService {

    public static final int SHOP_CATEGORY_MAX_COUNT = 3;

    private final ShopRepository shopRepository;

    public void duplicateShopCheck(ShopType type, String businessNumber) {
        this.duplicateShopCheck(type, businessNumber, null);
    }

    public void duplicateShopCheck(ShopType shopType, String businessNumber, Long shopId) {
        Optional.ofNullable(shopId)
            .ifPresentOrElse(id -> {
                if (shopRepository.countByTypeAndBusinessNumberAndIdNot(shopType, businessNumber, id) > 0)
                    throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_SHOP);
            }, () -> {
                if (shopRepository.countByTypeAndBusinessNumber(shopType, businessNumber) > 0)
                    throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_SHOP);
            });
    }

    public Shop getShop(long shopId) {
        return shopRepository.findById(shopId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_SHOP));
    }

}
