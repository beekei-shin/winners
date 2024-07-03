package org.winners.app.application.user;

import org.springframework.stereotype.Service;
import org.winners.app.application.user.dto.SignInBusinessUserResultDTO;

@Service
public interface BusinessUserSignService {
    SignInBusinessUserResultDTO signIn(String phoneNumber, String password);
}
