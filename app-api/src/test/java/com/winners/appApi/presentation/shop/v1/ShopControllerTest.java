package com.winners.appApi.presentation.shop.v1;

import com.winners.appApi.presentation.shop.v1.ShopController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import com.winners.appApi.application.shop.ShopService;
import com.winners.appApi.application.shop.dto.ShopListDTO;
import com.winners.appApi.config.ControllerTest;
import com.winners.appApi.config.WithMockUser;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.shop.ShopType;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ShopControllerTest extends ControllerTest {

    @MockBean
    private ShopService shopService;

    protected ShopControllerTest() {
        super(ShopController.class);
    }

    @Test
    @DisplayName("매장 목록 조회")
    @WithMockUser(authorities = TokenRole.CLIENT_USER)
    void getShopList() {
        List<ShopListDTO> shopList = List.of(
            new ShopListDTO(1L, ShopType.RESTAURANT, "카테고리1", "상점명1", "1111-1111-1111", List.of("0211111111"), "우편번호1", "111", "111"),
            new ShopListDTO(2L, ShopType.RESTAURANT, "카테고리2", "상점명2", "2222-2222-2222", List.of("0222222222"), "우편번호2", "222", "222"),
            new ShopListDTO(3L, ShopType.RESTAURANT, "카테고리3", "상점명3", "3333-3333-3333", List.of("0233333333"), "우편번호3", "333", "333"));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ShopListDTO> shopListPage = new PageImpl<>(shopList, pageRequest, shopList.size());
        given(shopService.getShopList(any(), any(PageRequest.class))).willReturn(shopListPage);

        mvcTest(HttpMethod.GET)
            .responseType(ApiResponseType.SUCCESS)
            .requestBody(mergeResponse(
                createPageResponse(shopListPage),
                createListResponse(shopList, "shopList",
                    Map.entry("shopId", ShopListDTO::getShopId),
                    Map.entry("shopType", ShopListDTO::getShopType),
                    Map.entry("shopName", ShopListDTO::getShopName),
                    Map.entry("categoryNames", ShopListDTO::getCategoryNames),
                    Map.entry("businessNumber", ShopListDTO::getBusinessNumber),
                    Map.entry("telNumber", ShopListDTO::getTelNumber),
                    Map.entry("address", dto -> String.format("(%s) %s %s", dto.getZipCode(), dto.getAddress(), dto.getDetailAddress())))))
            .run();
    }

}