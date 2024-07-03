package com.winners.appApi.application.shop;

import com.winners.appApi.application.shop.dto.GetShopListSearchParameterDTO;
import com.winners.appApi.application.shop.dto.ShopListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {
    Page<ShopListDTO> getShopList(GetShopListSearchParameterDTO searchParameter, PageRequest pageRequest);
}
