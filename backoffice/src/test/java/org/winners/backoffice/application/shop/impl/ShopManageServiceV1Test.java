package org.winners.backoffice.application.shop.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.winners.backoffice.config.ApplicationServiceTest;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.shop.*;
import org.winners.core.domain.shop.service.ShopDomainService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

class ShopManageServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private ShopManageServiceV1 shopManageServiceV1;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ShopDomainService shopDomainService;

    @Test
    @DisplayName("상점 등록")
    void saveShop() {
        Shop shop = ShopMock.createShop(1L);
        willDoNothing().given(shopDomainService).duplicateShopCheck(any(ShopType.class), anyString());
        given(shopRepository.saveAndFlush(any(Shop.class))).willReturn(shop);

        ShopType shopType = ShopType.RESTAURANT;
        String shopName = "테스트 상점";
        String businessNumber = "1234567890";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        shopManageServiceV1.saveShop(shopType, shopName, businessNumber, zipCode, address, detailAddress, categoryIds);

        assertThat(shop.getCategoryIds().size()).isEqualTo(categoryIds.size());
        shop.getCategoryIds().forEach(categoryId -> assertThat(categoryIds.contains(categoryId)).isTrue());

        verify(shopDomainService).duplicateShopCheck(shopType, businessNumber);
    }

    @Test
    @DisplayName("상점 등록 - 중복된 싱점")
    void saveShop_duplicatedShop() {
        willThrow(new DuplicatedDataException(ExceptionMessageType.DUPLICATED_SHOP))
            .given(shopDomainService).duplicateShopCheck(any(ShopType.class), anyString());

        ShopType shopType = ShopType.RESTAURANT;
        String shopName = "테스트 상점";
        String businessNumber = "1234567890";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> shopManageServiceV1.saveShop(shopType, shopName, businessNumber, zipCode, address, detailAddress, categoryIds));

        verify(shopDomainService).duplicateShopCheck(shopType, businessNumber);
    }

    @Test
    @DisplayName("상점 수정")
    void updateShop() {
        Shop shop = ShopMock.createShop(1L);
        given(shopDomainService.getShop(anyLong())).willReturn(shop);
        willDoNothing().given(shopDomainService).duplicateShopCheck(any(ShopType.class), anyString(), anyLong());

        long shopId = 1L;
        String shopName = "테스트 상점222";
        String businessNumber = "1234567890222";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        shopManageServiceV1.updateShop(shopId, shopName, businessNumber, zipCode, address, detailAddress, categoryIds);

        assertThat(shop.getName()).isEqualTo(shopName);
        assertThat(shop.getBusinessNumber()).isEqualTo(businessNumber);

        assertThat(shop.getCategoryIds().size()).isEqualTo(categoryIds.size());
        shop.getCategoryIds().forEach(categoryId -> assertThat(categoryIds.contains(categoryId)).isTrue());

        verify(shopDomainService).getShop(shopId);
        verify(shopDomainService).duplicateShopCheck(shop.getType(), businessNumber, shopId);
    }

    @Test
    @DisplayName("상점 수정 - 존재하지 않는 상점")
    void updateShop_notExistShop() {
        Shop shop = ShopMock.createShop(1L);
        given(shopDomainService.getShop(anyLong())).willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_SHOP));

        long shopId = 1L;
        String shopName = "테스트 상점222";
        String businessNumber = "1234567890222";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> shopManageServiceV1.updateShop(shopId, shopName, businessNumber, zipCode, address, detailAddress, categoryIds));

        verify(shopDomainService).getShop(shopId);
    }

    @Test
    @DisplayName("상점 수정 - 중복된 상점")
    void updateShop_duplicatedShop() {
        Shop shop = ShopMock.createShop(1L);
        given(shopDomainService.getShop(anyLong())).willReturn(shop);
        willThrow(new DuplicatedDataException(ExceptionMessageType.DUPLICATED_SHOP))
            .given(shopDomainService).duplicateShopCheck(any(ShopType.class), anyString(), anyLong());

        long shopId = 1L;
        String shopName = "테스트 상점222";
        String businessNumber = "1234567890222";
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Set<Long> categoryIds = Set.of(1L, 2L, 3L);
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> shopManageServiceV1.updateShop(shopId, shopName, businessNumber, zipCode, address, detailAddress, categoryIds));

        verify(shopDomainService).getShop(shopId);
        verify(shopDomainService).duplicateShopCheck(shop.getType(), businessNumber, shopId);
    }

    @Test
    @DisplayName("상점 삭제")
    void deleteShop() {
        Shop shop = ShopMock.createShop(1L);
        given(shopDomainService.getShop(anyLong())).willReturn(shop);

        long shopId = 1;
        shopManageServiceV1.deleteShop(shopId);

        assertThat(shop.getStatus()).isEqualTo(ShopStatus.DELETE);
        verify(shopDomainService).getShop(shopId);
    }

    @Test
    @DisplayName("상점 삭제 - 존재하지 않는 상점")
    void deleteShop_notExistShop() {
        given(shopDomainService.getShop(anyLong())).willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_SHOP));

        long shopId = 1;
        Throwable exception = assertThrows(NotExistDataException.class, () -> shopManageServiceV1.deleteShop(shopId));

        verify(shopDomainService).getShop(shopId);
    }

}