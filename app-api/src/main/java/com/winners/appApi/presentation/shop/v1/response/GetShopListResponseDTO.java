package com.winners.appApi.presentation.shop.v1.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import com.winners.appApi.application.shop.dto.ShopListDTO;
import org.winners.core.config.presentation.PagingResponseDTO;
import org.winners.core.domain.shop.ShopType;

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
                .shopName(shop.getShopName())
                .categoryNames(shop.getCategoryNames())
                .businessNumber(shop.getBusinessNumber())
                .telNumber(shop.getTelNumber())
                .address(String.format("(%s) %s %s", shop.getZipCode(), shop.getAddress(), shop.getDetailAddress()))
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
        private String shopName;
        private String categoryNames;
        private String businessNumber;
        private List<String> telNumber;
        private String address;
    }

}
