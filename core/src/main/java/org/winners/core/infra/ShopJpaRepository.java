package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.shop.Shop;
import org.winners.core.domain.shop.ShopRepository;

public interface ShopJpaRepository extends JpaRepository<Shop, Long>, ShopRepository {
}
