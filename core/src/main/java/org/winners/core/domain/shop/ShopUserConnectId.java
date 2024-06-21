package org.winners.core.domain.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ShopUserConnectId implements Serializable {

    @Comment("상점 고유번호")
    @Column(name = "shop_id", nullable = false, updatable = false)
    private Long shopId;

    @Comment("회원 고유번호")
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

}
