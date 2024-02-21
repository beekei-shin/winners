package org.winners.core.domain.auth.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.winners.core.domain.auth.PhoneIdentityAuthenticationHistory;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PhoneIdentityAuthenticationInfoDTO {
    private String name;
    private String phoneNumber;
    private String ci;
    private String di;
    private LocalDate birthday;
    private Gender gender;

    public static PhoneIdentityAuthenticationInfoDTO create(PhoneIdentityAuthenticationHistory phoneIdentityAuthenticationHistory) {
        return PhoneIdentityAuthenticationInfoDTO.builder()
            .name(phoneIdentityAuthenticationHistory.getName())
            .phoneNumber(phoneIdentityAuthenticationHistory.getPhoneNumber())
            .ci(phoneIdentityAuthenticationHistory.getCi())
            .di(phoneIdentityAuthenticationHistory.getDi())
            .birthday(phoneIdentityAuthenticationHistory.getBirthday())
            .gender(phoneIdentityAuthenticationHistory.getGender())
            .build();
    }

}
