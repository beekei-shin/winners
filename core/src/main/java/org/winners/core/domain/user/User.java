package org.winners.core.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.BaseEntity;

@Comment("회원")
@Getter
@Entity
@Table(name = "\"user\"", indexes = {
    @Index(name="IDX_user_user_type", columnList = "user_type"),
    @Index(name="IDX_user_user_status", columnList = "user_status"),
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", length = 50, discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("회원 유형")
    @Column(name = "user_type", length = 50, nullable = false, insertable = false, updatable = false)
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Comment("회원 상태")
    @Column(name = "user_status", length = 50, nullable = false)
    private UserStatus status;

    @Comment("회원 성명")
    @Column(name = "user_name", length = 100, nullable = false)
    private String name;

    protected User(UserType type, String name) {
        this.type = type;
        this.status = UserStatus.ACTIVE;
        this.name = name;
    }

    public boolean isBlockUser() {
        return this.status.equals(UserStatus.BLOCK);
    }

}
