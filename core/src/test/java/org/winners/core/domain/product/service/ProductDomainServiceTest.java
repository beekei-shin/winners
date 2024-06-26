package org.winners.core.domain.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.domain.product.Product;
import org.winners.core.domain.product.ProductMock;
import org.winners.core.domain.product.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ProductDomainServiceTest extends DomainServiceTest {

    private ProductDomainService productDomainService;
    private ProductRepository productRepository;

    @BeforeEach
    public void BeforeEach() {
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.productDomainService = new ProductDomainService(this.productRepository);
    }

    @Test
    @DisplayName("상품 조회")
    void getProduct() {
        Product product = ProductMock.createProduct(1L);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        long productId = 1;
        Product returnProduct = productDomainService.getProduct(productId);

        assertThat(returnProduct).isEqualTo(product);
        verify(productRepository).findById(productId);
    }

}