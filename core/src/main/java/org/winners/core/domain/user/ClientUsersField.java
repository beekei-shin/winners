package org.winners.core.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("사용자 회원 분야")
@Getter
@Entity
@Table(name = "client_users_field")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientUsersField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("사용자 회원 고유번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_user_id", nullable = false)
    private ClientUser clientUser;

    @Comment("분야 고유번호")
    @Column(name = "field_id", nullable = false, updatable = false)
    private Long fieldId;

}
