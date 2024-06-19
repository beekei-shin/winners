package org.winners.core.domain.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.shop.Shop;
import org.winners.core.domain.shop.ShopMock;
import org.winners.core.domain.shop.ShopRepository;
import org.winners.core.domain.shop.ShopType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ShopDomainServiceTest extends DomainServiceTest {

    private ShopDomainService shopDomainService;

    private ShopRepository shopRepository;

    @BeforeEach
    public void beforeEach() {
        this.shopRepository = Mockito.mock(ShopRepository.class);
        this.shopDomainService = new ShopDomainService(this.shopRepository);
    }

    @Test
    @DisplayName("중복 상점 확인")
    void duplicateShopCheck() {
        ShopType shopType = ShopType.RESTAURANT;
        String businessNumber = "1234567890";
        long shopId = 1L;

        given(shopRepository.countByTypeAndBusinessNumber(any(ShopType.class), anyString())).willReturn(0L);
        shopDomainService.duplicateShopCheck(shopType, businessNumber);
        verify(shopRepository).countByTypeAndBusinessNumber(shopType, businessNumber);

        given(shopRepository.countByTypeAndBusinessNumberAndIdNot(any(ShopType.class), anyString(), anyLong())).willReturn(0L);
        shopDomainService.duplicateShopCheck(shopType, businessNumber, shopId);
        verify(shopRepository).countByTypeAndBusinessNumberAndIdNot(shopType, businessNumber, shopId);
    }

    @Test
    @DisplayName("중복 상점 확인 - 중복된 상점")
    void duplicateShopCheck_duplicatedShop() {
        ShopType shopType = ShopType.RESTAURANT;
        String businessNumber = "1234567890";
        long shopId = 1L;

        given(shopRepository.countByTypeAndBusinessNumber(any(ShopType.class), anyString())).willReturn(1L);
        Throwable exception1 = assertThrows(DuplicatedDataException.class,
            () -> shopDomainService.duplicateShopCheck(shopType, businessNumber));
        assertThat(exception1.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_SHOP.getMessage());
        verify(shopRepository).countByTypeAndBusinessNumber(shopType, businessNumber);

        given(shopRepository.countByTypeAndBusinessNumberAndIdNot(any(ShopType.class), anyString(), anyLong())).willReturn(1L);
        Throwable exception2 = assertThrows(DuplicatedDataException.class,
            () -> shopDomainService.duplicateShopCheck(shopType, businessNumber, shopId));
        assertThat(exception2.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_SHOP.getMessage());
        verify(shopRepository).countByTypeAndBusinessNumberAndIdNot(shopType, businessNumber, shopId);
    }

    @Test
    @DisplayName("상점 조회")
    void getShop() {
        Shop shop = ShopMock.createShop(1L);
        given(shopRepository.findById(anyLong())).willReturn(Optional.of(shop));

        long shopId = 1;
        Shop returnShop = shopDomainService.getShop(shopId);

        assertThat(returnShop).isEqualTo(shop);
        verify(shopRepository).findById(shopId);
    }

    @Test
    @DisplayName("상점 조회 - 존재하지 않는 상점")
    void getShop_notExistShop() {
        given(shopRepository.findById(anyLong())).willReturn(Optional.empty());

        long shopId = 1;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> shopDomainService.getShop(shopId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_SHOP.getMessage());
        verify(shopRepository).findById(shopId);
    }

}