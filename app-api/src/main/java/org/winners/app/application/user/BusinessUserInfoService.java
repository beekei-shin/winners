package org.winners.app.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface BusinessUserInfoService {
    @Transactional(readOnly = true)
    boolean isUpdatedPassword(long userId);
    @Transactional
    void updatePassword(long userId, String password);
}
