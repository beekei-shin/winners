package com.winners.backofficeApi.presentation.shop.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.winners.backofficeApi.application.shop.dto.ShopInfoDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetShopInfoResponseDTO {

    @Getter
    @Builder
    public static class ShopUserResponseDTO {
        private final long userId;
        private final String userName;
        private final String phoneNumber;
    }

    private final long shopId;
    private final ShopType shopType;
    private final ShopStatus shopStatus;
    private final String shopName;
    private final String businessNumber;
    private final String zipCode;
    private final String address;
    private final String detailAddress;
    private final List<ShopUserResponseDTO> userList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
    private final LocalDateTime createdDatetime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
    private final LocalDateTime updatedDatetime;

    public static GetShopInfoResponseDTO convert(ShopInfoDTO shopInfo) {
        return GetShopInfoResponseDTO.builder()
            .shopId(shopInfo.getShopId())
            .shopType(shopInfo.getShopType())
            .shopStatus(shopInfo.getShopStatus())
            .shopName(shopInfo.getShopName())
            .businessNumber(shopInfo.getBusinessNumber())
            .zipCode(shopInfo.getZipCode())
            .address(shopInfo.getAddress())
            .detailAddress(shopInfo.getDetailAddress())
            .userList(shopInfo.getUserList().stream()
                .map(userInfo -> ShopUserResponseDTO.builder()
                    .userId(userInfo.getUserId())
                    .userName(userInfo.getUserName())
                    .phoneNumber(userInfo.getPhoneNumber())
                    .build())
                .collect(Collectors.toList()))
            .createdDatetime(shopInfo.getCreatedDatetime())
            .updatedDatetime(shopInfo.getUpdatedDatetime())
            .build();
    }

}
