package org.winners.core.domain.shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("상점 카테고리 연결")
@Getter
@Entity
@Table(name = "shop_category_connect", uniqueConstraints = {
    @UniqueConstraint(name = "UK_shop_category_connect_shop_id_category_id", columnNames = { "shop_id", "category_id" })
})
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopCategoryConnect extends BaseEntity {

    @EmbeddedId
    private ShopCategoryConnectId id;

    @MapsId("shopId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

    public static ShopCategoryConnect create(Shop shop, long categoryId) {
        return ShopCategoryConnect.builder().id(new ShopCategoryConnectId(shop.getId(), categoryId)).shop(shop).build();
    }

    public long getShopId() {
        return this.id.getShopId();
    }

    public long getCategoryId() {
        return this.id.getCategoryId();
    }

}
