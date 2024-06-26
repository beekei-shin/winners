package org.winners.core.domain.shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;
import org.winners.core.config.converter.ListConverter;
import org.winners.core.domain.common.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;

@Comment("상점")
@Getter
@Entity
@Table(name = "shop", uniqueConstraints = {
    @UniqueConstraint(name = "UK_shop_type_business_number", columnNames = { "type", "business_number" })
})
@Where(clause = "shop_status != 'DELETE'")
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Comment("상점 유형")
    @Column(name = "shop_type", length = 50, nullable = false, updatable = false)
    private ShopType type;

    @Enumerated(EnumType.STRING)
    @Comment("상점 유형")
    @Column(name = "shop_status", length = 50, nullable = false)
    private ShopStatus status;

    @Comment("상점명")
    @Column(name = "shop_name", length = 100, nullable = false)
    private String name;

    @Comment("사업자등록번호")
    @Column(name = "business_number", length = 100, nullable = false)
    private String businessNumber;

    @Convert(converter = ListConverter.class)
    @Comment("전화번호")
    @Column(name = "tel_number", length = 100, nullable = false)
    private List<String> telNumber;

    @Embedded
    private ShopAddress address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShopCategoryConnect> categoryConnectList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShopUserConnect> userConnectList;

    public static Shop createShop(ShopType type, String name, String businessNumber, List<String> telNumber, ShopAddress address) {
        return Shop.builder()
            .type(type)
            .status(ShopStatus.UNOPEN)
            .name(name)
            .telNumber(telNumber)
            .businessNumber(businessNumber)
            .address(address)
            .build();
    }

    public void updateShop(String name, String businessNumber, List<String> telNumber, ShopAddress address) {
        this.name = name;
        this.businessNumber = businessNumber;
        this.telNumber = telNumber;
        this.address = address;
    }

    public void open() {
        this.status = ShopStatus.OPEN;
    }

    public void unopen() {
        this.status = ShopStatus.UNOPEN;
    }

    public void close() {
        this.status = ShopStatus.CLOSE;
    }

    public void delete() {
        this.status = ShopStatus.DELETE;
    }

    public Set<Long> getCategoryIds() {
        return Optional.ofNullable(this.categoryConnectList)
            .map(list -> list.stream()
                .map(ShopCategoryConnect::getCategoryId)
                .collect(Collectors.toSet()))
            .orElseGet(HashSet::new);
    }

    public void connectCategories(Set<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return;
        if (this.categoryConnectList == null) this.categoryConnectList = new ArrayList<>();
        this.categoryConnectList.removeIf(connect -> !categoryIds.contains(connect.getCategoryId()));
        categoryIds.stream()
            .filter(categoryId -> !this.getCategoryIds().contains(categoryId))
            .forEach(categoryId -> this.categoryConnectList.add(ShopCategoryConnect.create(this, categoryId)));
    }

    public Set<Long> getUserIds() {
        return Optional.ofNullable(this.userConnectList)
            .map(list -> list.stream()
                .map(ShopUserConnect::getUserId)
                .collect(Collectors.toSet()))
            .orElseGet(HashSet::new);
    }

    public void connectUser(long userId) {
        if (this.userConnectList == null) this.userConnectList = new ArrayList<>();
        if (!this.getUserIds().contains(userId))
            this.userConnectList.add(ShopUserConnect.create(this, userId));
    }

    public void disconnectUser(long deleteUserId) {
        Optional.ofNullable(this.userConnectList)
            .ifPresent(connectList -> connectList.removeIf(connect -> connect.getUserId() == deleteUserId));
    }


}
