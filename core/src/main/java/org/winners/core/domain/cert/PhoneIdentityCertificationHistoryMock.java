package org.winners.core.domain.cert;

import org.winners.core.domain.base.Gender;

import java.time.LocalDate;

public class PhoneIdentityCertificationHistoryMock {

    public static PhoneIdentityCertificationHistory createHistory(CertificationKey certificationKey) {
        return PhoneIdentityCertificationHistory.create(
            certificationKey,
            "홍길동", LocalDate.of(1993, 10, 20), Gender.MALE,
            MobileCarrier.LG, "01011112222",
            "test-encryptOtpNumber");
    }

    public static PhoneIdentityCertificationHistory createCertifiedHistory(CertificationKey certificationKey) {
        PhoneIdentityCertificationHistory certificationHistory = createHistory(certificationKey);
        certificationHistory.certify("ci", "di");
        return certificationHistory;
    }

}
