package org.winners.core.domain.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Comment("인증키")
@Getter
@Entity
@Table(name = "authentication_key", uniqueConstraints = {
    @UniqueConstraint(name = "UK_authentication_key_authentication_key", columnNames = { "authentication_key" })
})
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationKey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("인증 유형")
    @Column(name = "authentication_type", length = 50, nullable = false, updatable = false)
    private AuthenticationType authenticationType;

    @Comment("인증키")
    @Column(name = "authentication_key", length = 16, nullable = false)
    private UUID authenticationKey;

    @Comment("사용여부")
    @Column(name = "used", columnDefinition = "TINYINT(1) DEFAULT 0", insertable = false)
    private boolean used;

    @Comment("사용일자")
    @Column(name = "used_datetime")
    private LocalDateTime usedDatetime;

    @Comment("만료일자")
    @Column(name = "expired_datetime", nullable = false)
    private LocalDateTime expiredDatetime;

    public boolean isExpired() {
        return expiredDatetime.isAfter(LocalDateTime.now());
    }

    public void use() {
        this.used = true;
        this.usedDatetime = LocalDateTime.now();
    }

}
