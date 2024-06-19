package org.winners.core.domain.shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("상점 카테고리")
@Getter
@Entity
@Table(name = "shop_category")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Comment("상점 유형")
    @Column(name = "shop_type", length = 50, nullable = false, insertable = false, updatable = false)
    private ShopType type;

    @Comment("카테고리명")
    @Column(name = "category_name", length = 100, nullable = false)
    private String name;

    @Comment("정렬순서")
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

}
