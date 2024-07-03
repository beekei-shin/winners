package com.winners.appApi.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winners.appApi.application.user.dto.SignInClientUserResultDTO;
import com.winners.appApi.application.user.dto.SignUpClientUserResultDTO;
import org.winners.core.domain.common.DeviceOs;

import java.util.UUID;

@Service
public interface ClientUserSignService {
    @Transactional
    SignUpClientUserResultDTO signUp(UUID certificationKey, DeviceOs deviceOs, String deviceToken);
    @Transactional
    SignInClientUserResultDTO signIn(UUID certificationKey, DeviceOs deviceOs, String deviceToken);
}
