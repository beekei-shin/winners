package org.winners.core.domain.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@Comment("상품")
@Getter
@Entity
@Table(name = "product")
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Comment("상점 고유번호")
    @Column(name = "shop_id", nullable = false, updatable = false)
    private Long shopId;

    @Comment("상품명")
    @Column(name = "product_name", length = 100, nullable = false)
    private String name;

    @Comment("상품원가")
    @Column(name = "product_cost", columnDefinition = "Decimal(15, 0)", nullable = false)
    private BigDecimal cost;

    @Comment("판매가격")
    @Column(name = "sale_price", columnDefinition = "Decimal(15, 0)", nullable = false)
    private BigDecimal salePrice;

    @Comment("할인가격")
    @Column(name = "discount_price", columnDefinition = "Decimal(15, 0)")
    private BigDecimal discountPrice;

    @OrderBy("sortOrder DESC")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductImage> imageList;

    public static Product createProduct(long shopId, String name, BigDecimal cost, BigDecimal salePrice, BigDecimal discountPrice) {
        return Product.builder()
            .shopId(shopId)
            .name(name)
            .cost(cost)
            .salePrice(salePrice)
            .discountPrice(discountPrice)
            .build();
    }

}
