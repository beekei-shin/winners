package org.winners.core.domain.cert;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.winners.core.domain.common.Gender;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneIdentityCertificationHistoryTest {

    @Test
    @DisplayName("휴대폰 본인인증 내역 생성")
    void createHistory() {
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        String name = "홍길동";
        LocalDate birthday = LocalDate.of(1993, 10, 20);
        Gender gender = Gender.MALE;
        MobileCarrier mobileCarrier = MobileCarrier.LGM;
        String phoneNumber = "01011112222";
        String otpNumber = "encodeOtpNumber";

        PhoneIdentityCertificationHistory history = PhoneIdentityCertificationHistory.createHistory(
            certificationKey,
            name, birthday, gender,
            mobileCarrier, phoneNumber,
            otpNumber);

        assertThat(history.getCertificationType()).isEqualTo(CertificationType.PHONE_IDENTITY);
        assertThat(history.getCertificationKey()).isEqualTo(certificationKey);
        assertThat(history.getName()).isEqualTo(name);
        assertThat(history.getBirthday()).isEqualTo(birthday);
        assertThat(history.getGender()).isEqualTo(gender);
        assertThat(history.getMobileCarrier()).isEqualTo(mobileCarrier);
        assertThat(history.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(history.getOtpNumber()).isEqualTo(otpNumber);
    }

    @Test
    @DisplayName("휴대폰 본인인증 내역 인증")
    void certify() {
        CertificationKey certificationKey = CertificationKeyMock.createKey(UUID.randomUUID());
        PhoneIdentityCertificationHistory history = PhoneIdentityCertificationHistoryMock.createHistory(certificationKey);

        String ci = "test_ci";
        String di = "test_di";
        boolean isForeigner = false;
        history.certify(ci, di, isForeigner);

        assertThat(history.getCi()).isEqualTo(ci);
        assertThat(history.getDi()).isEqualTo(di);
        assertThat(history.getIsForeigner()).isEqualTo(isForeigner);
    }

}