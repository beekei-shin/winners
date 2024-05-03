package org.winners.app.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;

import java.util.UUID;

@Service
public interface ClientUserSignService {

    @Transactional
    SignUpClientUserResultDTO signUp(UUID certificationKey);

    @Transactional
    SignInClientUserResultDTO signIn(UUID certificationKey);

}
