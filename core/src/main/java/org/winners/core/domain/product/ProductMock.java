package org.winners.core.domain.product;

import java.math.BigDecimal;

public class ProductMock {

    public static  Product createProduct(long id) {
        Product product = Product.createProduct(1L, "상품명", BigDecimal.valueOf(1000), BigDecimal.valueOf(1500), BigDecimal.valueOf(1300));
        product.id = id;
        return product;
    }

}
