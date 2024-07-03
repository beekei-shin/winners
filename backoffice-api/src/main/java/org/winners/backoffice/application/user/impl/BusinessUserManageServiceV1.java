package org.winners.backoffice.application.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.winners.backoffice.application.user.BusinessUserManageService;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserRepository;
import org.winners.core.domain.user.service.BusinessUserDomainService;

@Component
@RequiredArgsConstructor
public class BusinessUserManageServiceV1 implements BusinessUserManageService {

    private final PasswordEncoder passwordEncoder;
    private final BusinessUserRepository businessUserRepository;
    private final BusinessUserDomainService businessUserDomainService;

    @Override
    public void saveBusinessUser(String userName, String phoneNumber, String password) {
        businessUserDomainService.duplicateBusinessUserCheck(phoneNumber);

        String encodedPassword = passwordEncoder.encode(password);
        businessUserRepository.save(BusinessUser.createUser(userName, phoneNumber, encodedPassword));
    }

}
