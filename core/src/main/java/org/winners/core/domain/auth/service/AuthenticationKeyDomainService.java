package org.winners.core.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.AlreadyProcessedData;
import org.winners.core.config.exception.ExpiredDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.auth.AuthenticationKey;
import org.winners.core.domain.auth.AuthenticationKeyRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationKeyDomainService {

    private final AuthenticationKeyRepository authenticationKeyRepository;

    public void useAuthenticationKey(UUID authenticationKey) {
        final AuthenticationKey savedAuthenticationKey = authenticationKeyRepository.findByAuthenticationKey(authenticationKey)
            .orElseThrow(() -> new NotExistDataException("등록된 인증키가 없습니다."));
        if (savedAuthenticationKey.isUsed()) throw new AlreadyProcessedData("이미 사용된 인증키입니다.");
        if (savedAuthenticationKey.isExpired()) throw new ExpiredDataException("만료된 인증키입니다.");
        savedAuthenticationKey.use();
    }



}
