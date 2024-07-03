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
    private ShopManageService shopManageService;

    @Test
    @DisplayName("상점 등록")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void saveShop() {
        willDoNothing().given(shopManageService).saveShop(
            any(ShopType.class), anyString(), anyString(), anyList(),
            anyString(), anyString(), anyString(),
            anySet());

        ShopType shopType = ShopType.RESTAURANT;
        String shopName = "상점명";
        String businessNumber = "1234567890";
        List<String> telNumber = List.of("0211111111", "0222222222", "0333333333");
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        mvcTest(HttpMethod.POST)
            .requestBody(
                Map.entry("shopType", shopType),
                Map.entry("shopName", shopName),
                Map.entry("businessNumber", businessNumber),
                Map.entry("telNumber", telNumber),
                Map.entry("zipCode", zipCode),
                Map.entry("address", address),
                Map.entry("detailAddress", detailAddress),
                Map.entry("categoryIds", categoryIds))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageService).saveShop(shopType, shopName, businessNumber, telNumber, zipCode, address, detailAddress, categoryIds);
    }

    @Test
    @DisplayName("상점 수정")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void updateShop() {
        willDoNothing().given(shopManageService).updateShop(
            anyLong(), anyString(), anyString(), anyList(),
            anyString(), anyString(), anyString(),
            anySet());

        long shopId = 1;
        String shopName = "상점명";
        String businessNumber = "1234567890";
        List<String> telNumber = List.of("0211111111", "0222222222", "0333333333");
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        mvcTest("{shopId}", HttpMethod.PUT)
            .pathVariable(Map.entry("shopId", shopId))
            .requestBody(
                Map.entry("shopName", shopName),
                Map.entry("businessNumber", businessNumber),
                Map.entry("telNumber", telNumber),
                Map.entry("zipCode", zipCode),
                Map.entry("address", address),
                Map.entry("detailAddress", detailAddress),
                Map.entry("categoryIds", categoryIds))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageService).updateShop(shopId, shopName, businessNumber, telNumber, zipCode, address, detailAddress, categoryIds);
    }

    @Test
    @DisplayName("상점 삭제")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void deleteShop() {
        willDoNothing().given(shopManageService).deleteShop(anyLong());

        long shopId = 1;
        mvcTest(HttpMethod.DELETE)
            .requestBody(Map.entry("shopId", shopId))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageService).deleteShop(shopId);
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
        given(shopManageService.getShopList(any(), any(), any(PageRequest.class))).willReturn(shopPage);

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
        List<ShopInfoDTO.ShopUserInfoDTO> shopUserList = List.of(
            new ShopInfoDTO.ShopUserInfoDTO(2L, "홍길동1", "01011111111"),
            new ShopInfoDTO.ShopUserInfoDTO(3L, "홍길동2", "01022222222"),
            new ShopInfoDTO.ShopUserInfoDTO(4L, "홍길동3", "01033333333"));
        ShopInfoDTO shopInfo = new ShopInfoDTO(1L, ShopType.RESTAURANT, ShopStatus.OPEN, "상점1", "11111", "우편번호", "주소", "상세주소", shopUserList, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(1));
        given(shopManageService.getShopInfo(anyLong())).willReturn(shopInfo);

        long shopId = 1;
        mvcTest("{shopId}", HttpMethod.GET)
            .pathVariable(Map.entry("shopId", shopId))
            .responseType(ApiResponseType.SUCCESS)
            .responseBody(
                createResponse(
                    Map.entry("shopId", shopInfo.getShopId()),
                    Map.entry("shopType", shopInfo.getShopType().toString()),
                    Map.entry("shopStatus", shopInfo.getShopStatus().toString()),
                    Map.entry("shopName", shopInfo.getShopName()),
                    Map.entry("businessNumber", shopInfo.getBusinessNumber()),
                    Map.entry("zipCode", shopInfo.getZipCode()),
                    Map.entry("address", shopInfo.getAddress()),
                    Map.entry("detailAddress", shopInfo.getDetailAddress())),
                createListResponse(shopUserList, "userList",
                    Map.entry("userId", ShopInfoDTO.ShopUserInfoDTO::getUserId),
                    Map.entry("userName", ShopInfoDTO.ShopUserInfoDTO::getUserName),
                    Map.entry("phoneNumber", ShopInfoDTO.ShopUserInfoDTO::getPhoneNumber)),
                createResponse(
                    Map.entry("createdDatetime", DateUtil.formatDatetime(shopInfo.getCreatedDatetime())),
                    Map.entry("updatedDatetime", DateUtil.formatDatetime(shopInfo.getUpdatedDatetime()))))
            .run();

        verify(shopManageService).getShopInfo(shopId);
    }

    @Test
    @DisplayName("사업자 회원 상점 연결")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void connectShopToBusinessUser() {
        willDoNothing().given(shopManageService).connectShopToBusinessUser(anyLong(), anyLong());

        long shopId = 1;
        long userId = 2;
        mvcTest("business-user", HttpMethod.POST)
            .requestBody(
                Map.entry("shopId", shopId),
                Map.entry("userId", userId))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageService).connectShopToBusinessUser(shopId, userId);
    }

    @Test
    @DisplayName("사업자 회원 상점 연동 해제")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void disconnectShopToBusinessUser() {
        willDoNothing().given(shopManageService).disconnectShopToBusinessUser(anyLong(), anyLong());

        long shopId = 1;
        long userId = 2;
        mvcTest("business-user", HttpMethod.DELETE)
            .requestBody(
                Map.entry("shopId", shopId),
                Map.entry("userId", userId))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(shopManageService).disconnectShopToBusinessUser(shopId, userId);
    }

}