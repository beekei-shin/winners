package org.winners.core.domain.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ShopCategoryConnectId implements Serializable {

    @Comment("상점 고유번호")
    @Column(name = "shop_id", nullable = false, updatable = false)
    private Long shopId;

    @Comment("카테고리 고유번호")
    @Column(name = "category_id", nullable = false, updatable = false)
    private Long categoryId;

}
