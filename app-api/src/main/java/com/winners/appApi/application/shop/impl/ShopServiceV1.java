package com.winners.appApi.application.shop.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.winners.appApi.application.shop.ShopService;
import com.winners.appApi.application.shop.dto.GetShopListSearchParameterDTO;
import com.winners.appApi.application.shop.dto.ShopListDTO;
import org.winners.core.config.querydsl.QuerydslRepository;

import static org.winners.core.domain.shop.QShop.shop;
import static org.winners.core.domain.shop.QShopCategory.shopCategory;
import static org.winners.core.domain.shop.QShopCategoryConnect.shopCategoryConnect;

@Component
@RequiredArgsConstructor
public class ShopServiceV1 implements ShopService {

    private final QuerydslRepository querydslRepository;

    @Override
    public Page<ShopListDTO> getShopList(GetShopListSearchParameterDTO searchParameter, PageRequest pageRequest) {
        return querydslRepository
            .select(ShopListDTO.class, shop.id.countDistinct())
            .from(shop)
            .leftJoin(shopCategoryConnect, shopCategoryConnect.id.shopId.eq(shop.id))
            .leftJoin(shopCategory, shopCategory.id.eq(shopCategoryConnect.id.categoryId))
            .optionalWhereIn(searchParameter.getCategoryIds(), shopCategory.id)
            .optionalLikeOr(searchParameter.getKeyword(), shop.name, shop.businessNumber)
            .groupBy(shop.id)
            .getPage(pageRequest);
    }

}
