package org.winners.core.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.user.User;
import org.winners.core.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    public void accessUserCheck(long userId) {
        userRepository.findById(userId)
            .ifPresentOrElse(
                User::accessUserCheck,
                () -> { throw new NotExistDataException(ExceptionMessageType.NOT_EXIST_USER); });
    }

}
