package org.winners.core.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    @DisplayName("상품 생성")
    void createProduct() {
        long shopId = 1;
        String productName = "상품명";
        BigDecimal productCost = BigDecimal.valueOf(1000);
        BigDecimal salePrice = BigDecimal.valueOf(1500);
        BigDecimal discountPrice = BigDecimal.valueOf(1300);
        Product product = Product.createProduct(shopId, productName, productCost, salePrice, discountPrice);

        assertThat(product.getShopId()).isEqualTo(shopId);
        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getCost()).isEqualTo(productCost);
        assertThat(product.getSalePrice()).isEqualTo(salePrice);
        assertThat(product.getDiscountPrice()).isEqualTo(discountPrice);
    }

}