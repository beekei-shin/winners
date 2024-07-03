package com.winners.appApi.application.user.impl;

import com.winners.appApi.application.user.BusinessUserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.winners.appApi.application.user.dto.SignInBusinessUserResultDTO;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.InvalidDataException;
import org.winners.core.domain.auth.service.AuthDomainService;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.log.service.ServiceLogDomainService;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.service.BusinessUserDomainService;

@Component
@RequiredArgsConstructor
public class BusinessUserSignServiceV1 implements BusinessUserSignService {

    private final PasswordEncoder passwordEncoder;
    private final BusinessUserDomainService businessUserDomainService;
    private final ServiceLogDomainService serviceLogDomainService;
    private final AuthDomainService authDomainService;

    @Override
    public SignInBusinessUserResultDTO signIn(String phoneNumber, String password) {
        BusinessUser businessUser = businessUserDomainService.getBusinessUserByPhoneNumber(phoneNumber);
        if (!passwordEncoder.matches(password, businessUser.getPassword()))
            throw new InvalidDataException(ExceptionMessageType.INVALID_PASSWORD);

        boolean isUpdatedPassword = serviceLogDomainService.hasUpdatedPasswordLog(businessUser.getId());
        AuthTokenDTO authToken = authDomainService.createBusinessUserAuthToken(businessUser.getId());
        return SignInBusinessUserResultDTO.success(businessUser, isUpdatedPassword, authToken);
    }

}
