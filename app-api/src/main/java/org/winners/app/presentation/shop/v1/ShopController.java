package org.winners.app.presentation.shop.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.app.application.shop.ShopService;
import org.winners.app.application.shop.dto.GetShopListSearchParameterDTO;
import org.winners.app.application.shop.dto.ShopListDTO;
import org.winners.app.presentation.AppController;
import org.winners.app.presentation.shop.v1.response.GetShopListResponseDTO;
import org.winners.app.presentation.user.v1.request.IsUpdatedPasswordResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.PagingRequestDTO;
import org.winners.core.config.version.ApiVersion;
import org.winners.core.domain.shop.service.ShopDomainService;
import org.winners.core.utils.SecurityUtil;

import java.util.List;
import java.util.Set;

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
