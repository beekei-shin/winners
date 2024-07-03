package com.winners.backofficeApi.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface BusinessUserManageService {

    @Transactional
    void saveBusinessUser(String userName, String phoneNumber, String password);

}
