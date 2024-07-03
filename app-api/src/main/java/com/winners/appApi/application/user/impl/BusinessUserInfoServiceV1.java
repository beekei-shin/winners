package com.winners.appApi.application.user.impl;

import com.winners.appApi.application.user.BusinessUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.winners.core.domain.log.service.ServiceLogDomainService;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.service.BusinessUserDomainService;

@Component
@RequiredArgsConstructor
public class BusinessUserInfoServiceV1 implements BusinessUserInfoService {

    private final PasswordEncoder passwordEncoder;
    private final BusinessUserDomainService businessUserDomainService;
    private final ServiceLogDomainService serviceLogDomainService;

    @Override
    public boolean isUpdatedPassword(long userId) {
        BusinessUser businessUser = businessUserDomainService.getBusinessUser(userId);
        return serviceLogDomainService.hasUpdatedPasswordLog(businessUser.getId());
    }

    @Override
    public void updatePassword(long userId, String password) {
        BusinessUser businessUser = businessUserDomainService.getBusinessUser(userId);
        String encodedPassword = passwordEncoder.encode(password);
        businessUser.updatePassword(encodedPassword);
        serviceLogDomainService.saveUpdatePasswordLog(userId);
    }

}
