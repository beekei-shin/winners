package org.winners.app.application.user;

import org.springframework.stereotype.Service;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;

import java.util.UUID;

@Service
public interface ClientUserSignService {

    SignUpClientUserResultDTO signUpClientUser(UUID authenticationKey);

    SignInClientUserResultDTO signInClientUser(UUID authenticationKey);

}
