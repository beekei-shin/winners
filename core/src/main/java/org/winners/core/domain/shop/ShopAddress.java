package org.winners.core.domain.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;

@Embeddable
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopAddress {

    @Comment("우편번호")
    @Column(name = "zip_code", length = 100, nullable = false)
    private String zipCode;

    @Comment("주소")
    @Column(name = "address", nullable = false)
    private String address;

    @Comment("상세 주소")
    @Column(name = "detail_address")
    private String detailAddress;

    public static ShopAddress createAddress(String zipCode, String address, String detailAddress) {
        return ShopAddress.builder()
            .zipCode(zipCode)
            .address(address)
            .detailAddress(detailAddress)
            .build();
    }

}
