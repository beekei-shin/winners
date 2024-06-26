package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.product.Product;
import org.winners.core.domain.product.ProductRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {
}
