package org.winners.core.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.dto.SaveClientUserParameterDTO;

import static org.winners.core.config.exception.ExceptionMessageType.*;

@Service
@RequiredArgsConstructor
public class ClientUserDomainService {

    private final ClientUserRepository clientUserRepository;

    public void duplicatePhoneNumberCheck(String phoneNumber) {
        final long duplicatePhoneNumberCount = clientUserRepository.countByPhoneNumber(phoneNumber);
        if (duplicatePhoneNumberCount > 0) throw new DuplicatedDataException(DUPLICATED_PHONE_NUMBER);
    }

    public void duplicateCiCheck(String ci) {
        final long duplicateCiCount = clientUserRepository.countByCi(ci);
        if (duplicateCiCount > 0) throw new DuplicatedDataException(DUPLICATED_CI);
    }

    public ClientUser saveClientUser(SaveClientUserParameterDTO parameter) {
        return clientUserRepository.save(ClientUser.createUser(
            parameter.getName(),
            parameter.getPhoneNumber(),
            parameter.getCi(),
            parameter.getDi(),
            parameter.getBirthday(),
            parameter.getGender()));
    }

    public ClientUser getClientUser(long userId) {
        return clientUserRepository.findById(userId)
            .orElseThrow(() -> new NotExistDataException(NOT_EXIST_USER));
    }

}
