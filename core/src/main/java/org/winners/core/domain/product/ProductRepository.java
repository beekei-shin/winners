package org.winners.core.domain.product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(long productId);
}
