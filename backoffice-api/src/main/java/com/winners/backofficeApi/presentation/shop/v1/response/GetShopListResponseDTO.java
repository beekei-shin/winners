package com.winners.backofficeApi.presentation.shop.v1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.winners.backofficeApi.application.shop.dto.ShopListDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.winners.core.config.presentation.PagingResponseDTO;
import org.winners.core.domain.shop.ShopStatus;
import org.winners.core.domain.shop.ShopType;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetShopListResponseDTO extends PagingResponseDTO {

    public GetShopListResponseDTO(Page<ShopListDTO> shopList) {
        super(shopList);
        this.shopList = shopList.stream()
            .map(shop -> ShopDTO.builder()
                .shopId(shop.getShopId())
                .shopType(shop.getShopType())
                .shopStatus(shop.getShopStatus())
                .shopName(shop.getShopName())
                .businessNumber(shop.getBusinessNumber())
                .createdDatetime(shop.getCreatedDatetime())
                .build())
            .collect(Collectors.toList());
    }

    public static GetShopListResponseDTO convert(Page<ShopListDTO> shopList) {
        return new GetShopListResponseDTO(shopList);
    }

    private final List<ShopDTO> shopList;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class ShopDTO {
        private long shopId;
        private ShopType shopType;
        private ShopStatus shopStatus;
        private String shopName;
        private String businessNumber;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT_PATTERN)
        private LocalDateTime createdDatetime;
    }

}
