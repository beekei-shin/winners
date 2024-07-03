package org.winners.backoffice.presentation.shop.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.backoffice.application.shop.ShopManageService;
import org.winners.backoffice.application.shop.dto.GetShopListOrderByParameterDTO;
import org.winners.backoffice.application.shop.dto.GetShopListSearchParameterDTO;
import org.winners.backoffice.application.shop.dto.ShopInfoDTO;
import org.winners.backoffice.application.shop.dto.ShopListDTO;
import org.winners.backoffice.presentation.BackofficeController;
import org.winners.backoffice.presentation.shop.v1.request.*;
import org.winners.backoffice.presentation.shop.v1.response.GetShopInfoResponseDTO;
import org.winners.backoffice.presentation.shop.v1.response.GetShopListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.PagingRequestDTO;
import org.winners.core.config.version.ApiVersion;

import java.util.List;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = BackofficeController.SHOP_MANAGE_TAG_NAME)
@RequestMapping(path = BackofficeController.SHOP_MANAGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopManageController {

    private final ShopManageService shopManageServiceV1;

    @Operation(summary = "상점 등록")
    @PostMapping(name = "상점 등록", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> saveShop(@RequestBody @Valid SaveShopRequestDTO request) {
        shopManageServiceV1.saveShop(request.getShopType(), request.getShopName(), request.getBusinessNumber(), request.getTelNumber(),
            request.getZipCode(), request.getAddress(), request.getDetailAddress(),
            request.getCategoryIds());
        return ApiResponse.success();
    }

    @Operation(summary = "상점 수정")
    @PutMapping(name = "상점 수정", value = "{shopId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> updateShop(@PathVariable @Min(value = 1) long shopId,
                                     @RequestBody @Valid UpdateShopRequestDTO request) {
        shopManageServiceV1.updateShop(shopId, request.getShopName(), request.getBusinessNumber(), request.getTelNumber(),
            request.getZipCode(), request.getAddress(), request.getDetailAddress(),
            request.getCategoryIds());
        return ApiResponse.success();
    }

    @Operation(summary = "상점 삭제")
    @DeleteMapping(name = "상점 삭제", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> deleteShop(@RequestBody @Valid DeleteShopRequestDTO request) {
        shopManageServiceV1.deleteShop(request.getShopId());
        return ApiResponse.success();
    }

    @Operation(summary = "상점 목록 조회")
    @GetMapping(name = "상점 목록 조회", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<GetShopListResponseDTO> getShopList(@ParameterObject @ModelAttribute GetShopListSearchParameterDTO search,
                                                           @RequestParam(required = false) List<GetShopListOrderByParameterDTO> orderBy,
                                                           @ParameterObject @ModelAttribute PagingRequestDTO paging) {
        Page<ShopListDTO> shopList = shopManageServiceV1.getShopList(search, orderBy, paging.of());
        return ApiResponse.success(GetShopListResponseDTO.convert(shopList));
    }

    @Operation(summary = "상점 정보 조회")
    @GetMapping(name = "상점 정보 조회", value = "{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<GetShopInfoResponseDTO> getShopList(@PathVariable @Min(value = 1) long shopId) {
        ShopInfoDTO shopInfo = shopManageServiceV1.getShopInfo(shopId);
        return ApiResponse.success(GetShopInfoResponseDTO.convert(shopInfo));
    }

    @Operation(summary = "사업자 회원 상점 연결")
    @PostMapping(name = "사업자 회원 상점 연결", value = "business-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> connectShopToBusinessUser(@RequestBody @Valid ConnectShopToBusinessUserRequestDTO request) {
        shopManageServiceV1.connectShopToBusinessUser(request.getShopId(), request.getUserId());
        return ApiResponse.success();
    }

    @Operation(summary = "사업자 회원 상점 연결해제")
    @DeleteMapping(name = "사업자 회원 상점 연결해제", value = "business-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> connectShopToBusinessUser(@RequestBody @Valid DisconnectShopToBusinessUserRequestDTO request) {
        shopManageServiceV1.disconnectShopToBusinessUser(request.getShopId(), request.getUserId());
        return ApiResponse.success();
    }

}
