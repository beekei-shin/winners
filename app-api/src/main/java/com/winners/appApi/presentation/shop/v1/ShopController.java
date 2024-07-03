package com.winners.appApi.presentation.shop.v1;

import com.winners.appApi.presentation.AppController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.winners.appApi.application.shop.ShopService;
import com.winners.appApi.application.shop.dto.GetShopListSearchParameterDTO;
import com.winners.appApi.application.shop.dto.ShopListDTO;
import com.winners.appApi.presentation.shop.v1.response.GetShopListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.PagingRequestDTO;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.SHOP_TAG_NAME)
@RequestMapping(path = AppController.SHOP_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "상점 목록 조회")
    @GetMapping(name = "상점 목록 조회")
    public ApiResponse<GetShopListResponseDTO> getShopList(@ParameterObject @ModelAttribute GetShopListSearchParameterDTO search,
                                                           @ParameterObject @ModelAttribute PagingRequestDTO paging) {
        Page<ShopListDTO> shopList = shopService.getShopList(search, paging.of());
        return ApiResponse.success(GetShopListResponseDTO.convert(shopList));
    }



}
