package org.winners.backoffice.application.shop.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.winners.backoffice.application.shop.ShopManageService;
import org.winners.backoffice.application.shop.dto.GetShopListOrderByParameterDTO;
import org.winners.backoffice.application.shop.dto.GetShopListSearchParameterDTO;
import org.winners.backoffice.application.shop.dto.ShopInfoDTO;
import org.winners.backoffice.application.shop.dto.ShopListDTO;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.presentation.OrderByParameterDTO;
import org.winners.core.config.querydsl.QuerydslRepository;
import org.winners.core.domain.shop.Shop;
import org.winners.core.domain.shop.ShopAddress;
import org.winners.core.domain.shop.ShopRepository;
import org.winners.core.domain.shop.ShopType;
import org.winners.core.domain.shop.service.ShopDomainService;

import java.util.List;
import java.util.Set;

import static com.querydsl.core.group.GroupBy.groupBy;
import static org.winners.core.domain.shop.QShop.shop;
import static org.winners.core.domain.shop.QShopUserConnect.shopUserConnect;
import static org.winners.core.domain.user.QBusinessUser.businessUser;

@Component
@RequiredArgsConstructor
public class ShopManageServiceV1 implements ShopManageService {

    private final QuerydslRepository querydslRepository;
    private final ShopRepository shopRepository;
    private final ShopDomainService shopDomainService;

    @Override
    public void saveShop(ShopType shopType, String shopName, String businessNumber,
                         String zipCode, String address, String detailAddress,
                         Set<Long> categoryIds) {
        shopDomainService.duplicateShopCheck(shopType, businessNumber);
        Shop shop = shopRepository.saveAndFlush(Shop.createShop(shopType, shopName, businessNumber,
            ShopAddress.createAddress(zipCode, address, detailAddress)));
        shop.connectCategories(categoryIds);
    }

    @Override
    public void updateShop(long shopId, String shopName, String businessNumber,
                           String zipCode, String address, String detailAddress,
                           Set<Long> categoryIds) {
        Shop shop = shopDomainService.getShop(shopId);
        shopDomainService.duplicateShopCheck(shop.getType(), businessNumber, shop.getId());

        shop.updateShop(shopName, businessNumber, ShopAddress.createAddress(zipCode, address, detailAddress));
        shop.connectCategories(categoryIds);
    }

    @Override
    public void deleteShop(long shopId) {
        Shop shop = shopDomainService.getShop(shopId);
        shop.delete();
    }

    @Override
    public Page<ShopListDTO> getShopList(GetShopListSearchParameterDTO searchParameter,
                                         List<GetShopListOrderByParameterDTO> orderByParameter,
                                         PageRequest pageRequest) {
        return querydslRepository
            .select(ShopListDTO.class)
            .countSelect(shop.id.countDistinct())
            .from(shop)
            .optionalWhere(searchParameter.getShopType(), shop.type)
            .optionalWhere(searchParameter.getShopStatus(), shop.status)
            .optionalLikeOr(searchParameter.getKeyword(), shop.name, shop.businessNumber)
            .orderBy(OrderByParameterDTO.convertSpecifiers(orderByParameter, List.of(shop.id.desc())))
            .getPage(pageRequest);
    }

    @Override
    public ShopInfoDTO getShopInfo(long shopId) {
        return querydslRepository
            .from(shop)
            .leftJoin(shopUserConnect, shopUserConnect.shop.eq(shop))
            .leftJoin(businessUser, businessUser.id.eq(shopUserConnect.id.userId))
            .where(shopId, shop.id)
            .orderBy(shop.id.asc())
            .transformRow(groupBy(shop.id).as(new ShopInfoDTO().constructor()))
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_SHOP));
    }

    @Override
    public void connectShopToBusinessUser(long shopId, long userId) {
        Shop shop = shopDomainService.getShop(shopId);
        shop.connectUser(userId);
    }

    @Override
    public void disconnectShopToBusinessUser(long shopId, long userId) {
        Shop shop = shopDomainService.getShop(shopId);
        shop.disconnectUser(userId);
    }

}
