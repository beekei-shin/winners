package com.winners.appApi.application.user;

import org.springframework.stereotype.Service;
import com.winners.appApi.application.user.dto.SignInBusinessUserResultDTO;

@Service
public interface BusinessUserSignService {
    SignInBusinessUserResultDTO signIn(String phoneNumber, String password);
}
