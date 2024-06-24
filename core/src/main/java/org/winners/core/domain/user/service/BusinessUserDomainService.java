package org.winners.core.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserRepository;

@Service
@RequiredArgsConstructor
public class BusinessUserDomainService {

    private final BusinessUserRepository businessUserRepository;

    public void duplicateBusinessUserCheck(String phoneNumber) {
        if (businessUserRepository.countByPhoneNumber(phoneNumber) > 0)
            throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_PHONE_NUMBER);
    }

    public BusinessUser getBusinessUser(long userId) {
        return businessUserRepository.findById(userId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_USER));
    }

    public BusinessUser getBusinessUserByPhoneNumber(String phoneNumber) {
        return businessUserRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_USER));
    }

}
