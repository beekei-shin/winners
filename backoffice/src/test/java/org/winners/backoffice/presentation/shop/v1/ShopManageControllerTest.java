package org.winners.backoffice.presentation.shop.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.winners.backoffice.application.shop.ShopManageService;
import org.winners.backoffice.application.shop.dto.ShopInfoDTO;
import org.winners.backoffice.application.shop.dto.ShopListDTO;
import org.winners.backoffice.config.ControllerTest;
import org.winners.backoffice.config.WithMockUser;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class ShopManageControllerTest extends ControllerTest {

    protected ShopManageControllerTest() {
        super(ShopManageController.class);
    }

    @MockBean
    private ShopManageService shopManageServiceV1;

    @Test
    @DisplayName("상점 등록")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void saveShop() {
        willDoNothing().given(shopManageServiceV1).saveShop(
            any(ShopType.class), anyString(), anyString(),
            anyString(), anyString(), anyString(),
            anySet());

        ShopType shopType = ShopType.RESTAURANT;
        String shopName = "상점명";
        String businessNumber = "1234567890";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        mvcTest(HttpMethod.POST)
            .requestBody(
                Map.entry("shopType", shopType),
                Map.entry("shopName", shopName),
                Map.entry("businessNumber", businessNumber),
                Map.entry("zipCode", zipCode),
                Map.entry("address", address),
                Map.entry("detailAddress", detailAddress),
                Map.entry("categoryIds", categoryIds))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageServiceV1).saveShop(shopType, shopName, businessNumber, zipCode, address, detailAddress, categoryIds);
    }

    @Test
    @DisplayName("상점 수정")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void updateShop() {
        willDoNothing().given(shopManageServiceV1).updateShop(
            anyLong(), anyString(), anyString(),
            anyString(), anyString(), anyString(),
            anySet());

        long shopId = 1;
        String shopName = "상점명";
        String businessNumber = "1234567890";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        mvcTest("{shopId}", HttpMethod.PUT)
            .pathVariable(Map.entry("shopId", shopId))
            .requestBody(
                Map.entry("shopName", shopName),
                Map.entry("businessNumber", businessNumber),
                Map.entry("zipCode", zipCode),
                Map.entry("address", address),
                Map.entry("detailAddress", detailAddress),
                Map.entry("categoryIds", categoryIds))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageServiceV1).updateShop(shopId, shopName, businessNumber, zipCode, address, detailAddress, categoryIds);
    }

    @Test
    @DisplayName("상점 삭제")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void deleteShop() {
        willDoNothing().given(shopManageServiceV1).deleteShop(anyLong());

        long shopId = 1;
        mvcTest(HttpMethod.DELETE)
            .requestBody(Map.entry("shopId", shopId))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageServiceV1).deleteShop(shopId);
    }

    @Test
    @DisplayName("상점 목록 조회")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void getShopList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<ShopListDTO> shopList = List.of(
            new ShopListDTO(1L, ShopType.RESTAURANT, ShopStatus.OPEN, "상점1", "11111", LocalDateTime.now().minusDays(1)),
            new ShopListDTO(2L, ShopType.RESTAURANT, ShopStatus.OPEN, "상점2", "22222", LocalDateTime.now().minusDays(2)),
            new ShopListDTO(3L, ShopType.RESTAURANT, ShopStatus.OPEN, "상점2", "33333", LocalDateTime.now().minusDays(3))
        );
        Page<ShopListDTO> shopPage = new PageImpl<>(shopList, pageRequest, shopList.size());
        given(shopManageServiceV1.getShopList(any(), any(), any(PageRequest.class))).willReturn(shopPage);

        mvcTest(HttpMethod.GET)
            .responseType(ApiResponseType.SUCCESS)
            .requestParam(
                Map.entry("size", 10),
                Map.entry("page", 1))
            .responseBody(
                mergeResponse(
                    createPageResponse(shopPage),
                    createListResponse(shopPage.getContent(),
                        "shopList",
                        Map.entry("shopId", ShopListDTO::getShopId),
                        Map.entry("shopType", shop -> shop.getShopType().toString()),
                        Map.entry("shopStatus", shop -> shop.getShopStatus().toString()),
                        Map.entry("shopName", ShopListDTO::getShopName),
                        Map.entry("businessNumber", ShopListDTO::getBusinessNumber),
                        Map.entry("createdDatetime", shop -> DateUtil.formatDatetime(shop.getCreatedDatetime())))))
            .run();
    }

    @Test
    @DisplayName("상점 정보 조회")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void getShopInfo() {
        ShopInfoDTO shopInfo = new ShopInfoDTO(1L, ShopType.RESTAURANT, ShopStatus.OPEN, "상점1", "11111", "우편번호", "주소", "상세주소", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(1));
        given(shopManageServiceV1.getShopInfo(anyLong())).willReturn(shopInfo);

        long shopId = 1;
        mvcTest("{shopId}", HttpMethod.GET)
            .pathVariable(Map.entry("shopId", shopId))
            .responseType(ApiResponseType.SUCCESS)
            .responseBody(
                Map.entry("shopId", shopInfo.getShopId()),
                Map.entry("shopType", shopInfo.getShopType().toString()),
                Map.entry("shopStatus", shopInfo.getShopStatus().toString()),
                Map.entry("shopName", shopInfo.getShopName()),
                Map.entry("businessNumber", shopInfo.getBusinessNumber()),
                Map.entry("zipCode", shopInfo.getZipCode()),
                Map.entry("address", shopInfo.getAddress()),
                Map.entry("detailAddress", shopInfo.getDetailAddress()),
                Map.entry("createdDatetime", DateUtil.formatDatetime(shopInfo.getCreatedDatetime())),
                Map.entry("updatedDatetime", DateUtil.formatDatetime(shopInfo.getUpdatedDatetime())))
            .run();

        verify(shopManageServiceV1).getShopInfo(shopId);
    }

}