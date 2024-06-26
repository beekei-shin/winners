package org.winners.app.application.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.winners.app.application.shop.dto.GetShopListSearchParameterDTO;
import org.winners.app.application.shop.dto.ShopListDTO;

@Service
public interface ShopService {
    Page<ShopListDTO> getShopList(GetShopListSearchParameterDTO searchParameter, PageRequest pageRequest);
}
