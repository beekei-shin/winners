package org.winners.core.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("사업자 회원")
@Getter
@Entity
@Table(name = "business_user", uniqueConstraints = {
    @UniqueConstraint(name = "UK_business_user_phone_number", columnNames = { "phone_number" })
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("BUSINESS")
public class BusinessUser extends User {

    @Comment("휴대폰 번호")
    @Column(name = "", length = 20, nullable = false)
    private String phoneNumber;

    @Comment("비밀번호")
    @Column(name = "password", length = 300, nullable = false)
    private String password;

    private BusinessUser(String name, String phoneNumber, String password) {
        super(UserType.CLIENT, name);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public static BusinessUser createUser(String name, String phoneNumber, String password) {
        return new BusinessUser(name, phoneNumber, password);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
