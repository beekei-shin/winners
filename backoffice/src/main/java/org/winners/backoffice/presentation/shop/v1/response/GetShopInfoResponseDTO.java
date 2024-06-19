package org.winners.backoffice.presentation.shop.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.backoffice.application.shop.dto.ShopInfoDTO;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetShopInfoResponseDTO {

    private final long shopId;
    private final ShopType shopType;
    private final ShopStatus shopStatus;
    private final String shopName;
    private final String businessNumber;
    private final String zipCode;
    private final String address;
    private final String detailAddress;
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
            .createdDatetime(shopInfo.getCreatedDatetime())
            .updatedDatetime(shopInfo.getUpdatedDatetime())
            .build();
    }

}
