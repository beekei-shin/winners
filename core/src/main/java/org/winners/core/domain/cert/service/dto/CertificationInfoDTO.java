package org.winners.core.domain.cert.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistory;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CertificationInfoDTO {

    private String name;
    private String phoneNumber;
    private String ci;
    private String di;
    private LocalDate birthday;
    private Gender gender;

    public static CertificationInfoDTO convert(PhoneIdentityCertificationHistory phoneIdentityCertificationHistory) {
        return CertificationInfoDTO.builder()
            .name(phoneIdentityCertificationHistory.getName())
            .phoneNumber(phoneIdentityCertificationHistory.getPhoneNumber())
            .ci(phoneIdentityCertificationHistory.getCi())
            .di(phoneIdentityCertificationHistory.getDi())
            .birthday(phoneIdentityCertificationHistory.getBirthday())
            .gender(phoneIdentityCertificationHistory.getGender())
            .build();
    }

}
