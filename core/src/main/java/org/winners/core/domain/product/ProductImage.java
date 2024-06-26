package org.winners.core.domain.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.UploadFile;

@Comment("상품 이미지")
@Getter
@Entity
@Table(name = "product_image", uniqueConstraints = {
    @UniqueConstraint(name = "UK_product_image_product_id_sort_order", columnNames = { "product_id", "sort_order" })
})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PRODUCT_IMAGE")
public class ProductImage extends UploadFile {

    @Comment("상품 고유번호")
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long productId;

    @Comment("정렬 순서")
    @Column(name = "sort_order", columnDefinition = "TINYINT", nullable = false)
    private Integer sortOrder;

}
