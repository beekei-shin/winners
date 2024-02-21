package org.winners.core.domain.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.base.BaseEntity;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Comment("휴대폰 본인인증 내역")
@Getter
@Entity
@Table(name = "phone_identity_authentication_history", uniqueConstraints = {
    @UniqueConstraint(name = "UK_phone_identity_authentication_history_authentication_key", columnNames = { "authentication_key" })
})
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneIdentityAuthenticationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("휴대폰 번호")
    @Column(name = "phone_number", length = 300, nullable = false)
    private String phoneNumber;

    @Comment("CI")
    @Column(name = "ci", length = 500, nullable = false)
    private String ci;

    @Comment("DI")
    @Column(name = "di", length = 500)
    private String di;

    @Comment("성명")
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Comment("생년월일")
    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Comment("성별")
    @Column(name = "gender", length = 50)
    private Gender gender;

    @Comment("인증키")
    @Column(name = "authentication_key", length = 16, nullable = false)
    private UUID authenticationKey;

}
