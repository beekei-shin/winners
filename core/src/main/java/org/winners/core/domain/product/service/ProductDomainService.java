package org.winners.core.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.product.Product;
import org.winners.core.domain.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductDomainService {

    private final ProductRepository productRepository;

    public Product getProduct(long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_PRODUCT));
    }

}
