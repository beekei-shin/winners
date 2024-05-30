package org.winners.core.domain.cert;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.winners.core.domain.common.Gender;

import java.time.LocalDate;

@Comment("휴대폰 본인인증 내역")
@Getter
@Entity
@Table(name = "phone_identity_certification_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("PHONE_IDENTITY")
public class PhoneIdentityCertificationHistory extends CertificationHistory {

    @Comment("성명")
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Comment("생년월일")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Comment("성별")
    @Column(name = "gender", length = 50, nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Comment("통신사")
    @Column(name = "mobile_carrier", length = 50, nullable = false)
    private MobileCarrier mobileCarrier;

    @Comment("휴대폰 번호")
    @Column(name = "phone_number", length = 300, nullable = false)
    private String phoneNumber;

    @Comment("otp 번호")
    @Column(name = "otp_number", length = 100, nullable = false)
    private String otpNumber;

    @Comment("CI")
    @Column(name = "ci", length = 500)
    private String ci;

    @Comment("DI")
    @Column(name = "di", length = 500)
    private String di;

    @Comment("외국인 여부")
    @Column(name = "is_foreigner", columnDefinition = "TINYINT(1)")
    private Boolean isForeigner;

    private PhoneIdentityCertificationHistory(CertificationKey certificationKey,
                                              String name, LocalDate birthday, Gender gender,
                                              MobileCarrier mobileCarrier, String phoneNumber, String otpNumber) {
        super(CertificationType.PHONE_IDENTITY, certificationKey);
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.mobileCarrier = mobileCarrier;
        this.phoneNumber = phoneNumber;
        this.otpNumber = otpNumber;
    }

    public static PhoneIdentityCertificationHistory createHistory(CertificationKey certificationKey,
                                                                  String name, LocalDate birthday, Gender gender,
                                                                  MobileCarrier mobileCarrier, String phoneNumber, String otpNumber) {
        return new PhoneIdentityCertificationHistory(certificationKey,
            name, birthday, gender,
            mobileCarrier, phoneNumber, otpNumber);
    }

    public void certify(String ci, String di, boolean isForeigner) {
        this.ci = ci;
        this.di = di;
        this.isForeigner = isForeigner;
    }

}
