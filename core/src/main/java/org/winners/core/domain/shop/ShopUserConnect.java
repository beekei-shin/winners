package org.winners.core.domain.shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

@Comment("상점 회원 연결")
@Getter
@Entity
@Table(name = "shop_user_connect", uniqueConstraints = {
    @UniqueConstraint(name = "UK_shop_user_connect_shop_id_user_id", columnNames = { "shop_id", "user_id" })
})
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopUserConnect extends BaseEntity {

    @EmbeddedId
    private ShopUserConnectId id;

    @MapsId("shopId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

    public static ShopUserConnect create(Shop shop, long categoryId) {
        return ShopUserConnect.builder().id(new ShopUserConnectId(shop.getId(), categoryId)).shop(shop).build();
    }

    public long getShopId() {
        return this.id.getShopId();
    }

    public long getUserId() {
        return this.id.getUserId();
    }

}
