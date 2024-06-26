package org.winners.core.domain.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ShopTest {

    @Test
    @DisplayName("상점 생성")
    void createShop() {
        ShopType shopType = ShopType.RESTAURANT;
        String shopName = "테스트 상점";
        String businessNumber = "1234567890";
        List<String> telNumber = List.of("0211111111", "0222222222", "0233333333");
        String zipCode = "우편번호";
        String address = "주소";
        String detailAddress = "상세주소";
        Shop shop = Shop.createShop(shopType, shopName, businessNumber, telNumber, ShopAddress.createAddress(zipCode, address, detailAddress));

        assertThat(shop.getType()).isEqualTo(shopType);
        assertThat(shop.getName()).isEqualTo(shopName);
        assertThat(shop.getBusinessNumber()).isEqualTo(businessNumber);
        assertThat(shop.getTelNumber()).isEqualTo(telNumber);
        assertThat(shop.getAddress().getZipCode()).isEqualTo(zipCode);
        assertThat(shop.getAddress().getAddress()).isEqualTo(address);
        assertThat(shop.getAddress().getDetailAddress()).isEqualTo(detailAddress);
    }

    @Test
    @DisplayName("상점 수정")
    void updateShop() {
        Shop shop = ShopMock.createShop(1L);
        String updateShopName = "수정 상점명";
        String updateBusinessName = "수정 사업자등록번호";
        List<String> telNumber = List.of("0211111111", "0222222222", "0233333333");
        String zipCode = "수정 우편번호";
        String address = "수정 주소";
        String detailAddress = "수정 상세주소";
        shop.updateShop(updateShopName, updateBusinessName, telNumber, ShopAddress.createAddress(zipCode, address, detailAddress));

        assertThat(shop.getName()).isEqualTo(updateShopName);
        assertThat(shop.getBusinessNumber()).isEqualTo(updateBusinessName);
        assertThat(shop.getAddress().getZipCode()).isEqualTo(zipCode);
        assertThat(shop.getAddress().getAddress()).isEqualTo(address);
        assertThat(shop.getAddress().getDetailAddress()).isEqualTo(detailAddress);
    }


    @Test
    @DisplayName("상점 오픈")
    void open() {
        Shop shop = ShopMock.createShop(1L);
        shop.open();
        assertThat(shop.getStatus()).isEqualTo(ShopStatus.OPEN);
    }

    @Test
    @DisplayName("상점 미오픈")
    void unopen() {
        Shop shop = ShopMock.createShop(1L);
        shop.unopen();
        assertThat(shop.getStatus()).isEqualTo(ShopStatus.UNOPEN);
    }

    @Test
    @DisplayName("상점 폐업")
    void close() {
        Shop shop = ShopMock.createShop(1L);
        shop.close();
        assertThat(shop.getStatus()).isEqualTo(ShopStatus.CLOSE);
    }

    @Test
    @DisplayName("상점 삭제")
    void delete() {
        Shop shop = ShopMock.createShop(1L);
        shop.delete();
        assertThat(shop.getStatus()).isEqualTo(ShopStatus.DELETE);
    }

    @Test
    @DisplayName("카테고리 고유번호 조회")
    void getCategoryIds() {
        Set<Long> categoryIds = Set.of(2L, 3L, 4L);
        Shop shop = ShopMock.createShopWithCategory(1L, categoryIds);

        assertThat(shop.getCategoryIds().size()).isEqualTo(categoryIds.size());
        shop.getCategoryIds().forEach(categoryId -> assertThat(categoryIds).contains(categoryId));
    }

    @Test
    @DisplayName("카테고리 연결")
    void connectCategories() {
        Shop shop = ShopMock.createShop(1L);

        Set<Long> categoryIds1 = Set.of(1L, 2L, 3L);
        shop.connectCategories(categoryIds1);
        assertThat(shop.getCategoryConnectList().size()).isEqualTo(categoryIds1.size());
        shop.getCategoryConnectList().forEach(connect -> assertThat(categoryIds1.contains(connect.getId().getCategoryId())).isTrue());

        Set<Long> categoryIds2 = Set.of(3L, 4L);
        shop.connectCategories(categoryIds2);
        assertThat(shop.getCategoryConnectList().size()).isEqualTo(categoryIds2.size());
        shop.getCategoryConnectList().forEach(connect -> assertThat(categoryIds2.contains(connect.getId().getCategoryId())).isTrue());

        Set<Long> categoryIds3 = Set.of(5L);
        shop.connectCategories(categoryIds3);
        assertThat(shop.getCategoryConnectList().size()).isEqualTo(categoryIds3.size());
        shop.getCategoryConnectList().forEach(connect -> assertThat(categoryIds3.contains(connect.getId().getCategoryId())).isTrue());

        Set<Long> categoryIds4 = Set.of(); // 빈값 시 무시 테스트
        shop.connectCategories(categoryIds4);
        assertThat(shop.getCategoryConnectList().size()).isEqualTo(categoryIds3.size());
        shop.getCategoryConnectList().forEach(connect -> assertThat(categoryIds3.contains(connect.getId().getCategoryId())).isTrue());
    }

    @Test
    @DisplayName("회원 고유번호 조회")
    void getUserIds() {
        Set<Long> userIds = Set.of(2L, 3L, 4L);
        Shop shop = ShopMock.createShopWithUser(1L, userIds);

        assertThat(shop.getUserIds().size()).isEqualTo(userIds.size());
        shop.getUserIds().forEach(userId -> assertThat(userIds).contains(userId));
    }

    @Test
    @DisplayName("회원 연결")
    void connectUsers() {
        long userId = 2;
        Shop shop = ShopMock.createShop(1L);
        shop.connectUser(userId);

        assertThat(shop.getUserIds()).contains(userId);
    }

    @Test
    @DisplayName("회원 연결해제")
    void disconnectUser() {
        Set<Long> userIds = Set.of(2L, 3L, 4L);
        Shop shop = ShopMock.createShopWithUser(1L, userIds);

        long deleteUserId = 3L;
        shop.disconnectUser(deleteUserId);
        userIds = userIds.stream().filter(id -> id != 3L).collect(Collectors.toSet());

        assertThat(shop.getUserConnectList().size()).isEqualTo(userIds.size());
        assertThat(shop.getUserConnectList().stream().anyMatch(connect -> connect.getUserId() == deleteUserId)).isFalse();
    }

}