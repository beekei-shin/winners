package org.winners.core.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.auth.PhoneIdentityAuthenticationHistory;
import org.winners.core.domain.auth.PhoneIdentityAuthenticationHistoryRepository;
import org.winners.core.domain.auth.service.dto.PhoneIdentityAuthenticationInfoDTO;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhoneIdentityAuthenticationDomainService {

    private final PhoneIdentityAuthenticationHistoryRepository phoneIdentityAuthenticationHistoryRepository;

    public PhoneIdentityAuthenticationInfoDTO getPhoneIdentityAuthenticationInfo(UUID authenticationKey) {
        return phoneIdentityAuthenticationHistoryRepository.findByAuthenticationKey(authenticationKey)
            .map(PhoneIdentityAuthenticationInfoDTO::create)
            .orElseThrow(() -> new NotExistDataException("휴대폰 본인인증 내역이 없습니다."));
    }

}
