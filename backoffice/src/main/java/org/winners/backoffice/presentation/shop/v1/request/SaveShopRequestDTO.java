package org.winners.backoffice.presentation.shop.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.winners.core.domain.shop.ShopType;
import org.winners.core.domain.shop.service.ShopDomainService;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveShopRequestDTO {

    @NotNull
    private ShopType shopType;

    @NotBlank
    @Length(max = 50)
    private String shopName;

    @NotBlank
    @Length(max = 50)
    private String businessNumber;

    @NotBlank
    @Length(max = 50)
    private String zipCode;

    @NotBlank
    @Length(max = 200)
    private String address;

    @Length(max = 200)
    private String detailAddress;

    @NotNull
    @Size(min = 1, max = ShopDomainService.SHOP_CATEGORY_MAX_COUNT)
    private Set<Long> categoryIds;

}
