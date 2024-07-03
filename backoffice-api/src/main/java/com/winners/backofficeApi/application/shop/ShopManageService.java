package com.winners.backofficeApi.application.shop;

import com.winners.backofficeApi.application.shop.dto.GetShopListOrderByParameterDTO;
import com.winners.backofficeApi.application.shop.dto.GetShopListSearchParameterDTO;
import com.winners.backofficeApi.application.shop.dto.ShopListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winners.backofficeApi.application.shop.dto.ShopInfoDTO;
import org.winners.core.domain.shop.ShopType;

import java.util.List;
import java.util.Set;

@Service
public interface ShopManageService {

    @Transactional
    void saveShop(ShopType shopType, String shopName, String businessNumber, List<String> telNumber,
                  String zipCode, String address, String detailAddress,
                  Set<Long> categoryIds);

    @Transactional
    void updateShop(long shopId, String shopName, String businessNumber, List<String> telNumber,
                    String zipCode, String address, String detailAddress,
                    Set<Long> categoryIds);

    @Transactional
    void deleteShop(long shopId);

    @Transactional(readOnly = true)
    Page<ShopListDTO> getShopList(GetShopListSearchParameterDTO searchParameter,
                                  List<GetShopListOrderByParameterDTO> orderByParameter,
                                  PageRequest pageRequest);

    @Transactional(readOnly = true)
    ShopInfoDTO getShopInfo(long shopId);

    @Transactional
    void connectShopToBusinessUser(long shopId, long userId);

    @Transactional
    void disconnectShopToBusinessUser(long shopId, long userId);

}
