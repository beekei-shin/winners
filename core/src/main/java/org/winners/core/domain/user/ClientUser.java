package org.winners.core.domain.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;
import java.util.List;

@Comment("클라이언트 회원")
@DynamicInsert
@Getter
@Entity
@Table(name = "client_user", uniqueConstraints = {
    @UniqueConstraint(name = "UK_client_user_ci", columnNames = { "ci" }),
    @UniqueConstraint(name = "UK_client_user_phone_number", columnNames = { "phone_number" })
})
@DiscriminatorValue("CLIENT_USER")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientUser extends User {

    @Comment("휴대폰 번호")
    @Column(name = "phone_number", length = 300, nullable = false)
    private String phoneNumber;

    @Comment("CI")
    @Column(name = "ci", length = 500, nullable = false)
    private String ci;

    @Comment("DI")
    @Column(name = "di", length = 500)
    private String di;

    @Comment("회원 생년월일")
    @Column(name = "user_birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Comment("회원 성별")
    @Column(name = "user_gender", length = 50)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientUser")
    private List<ClientUsersField> fieldList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientUser")
    private List<ClientUsersJob> jobList;

    private ClientUser(String name, String phoneNumber, String ci, @Nullable String di, @Nullable LocalDate birthday, @Nullable Gender gender) {
        super(UserType.CLIENT, name);
        this.phoneNumber = phoneNumber;
        this.ci = ci;
        this.di = di;
        this.birthday = birthday;
        this.gender = gender;
    }

    public static ClientUser create(String name, String phoneNumber, String ci, @Nullable String di, @Nullable LocalDate birthday, @Nullable Gender gender) {
        return new ClientUser(name, phoneNumber, ci, di, birthday, gender);
    }

}
