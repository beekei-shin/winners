package org.winners.core.domain.user.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.base.Gender;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClientUserDomainService {

    private final ClientUserRepository clientUserRepository;

    public boolean duplicatePhoneNumberCheck(String phoneNumber) {
        final long duplicatePhoneNumberCount = clientUserRepository.countByPhoneNumber(phoneNumber);
        return duplicatePhoneNumberCount > 0;
    }

    public boolean duplicateCiCheck(String ci) {
        final long duplicateCiCount = clientUserRepository.countByCi(ci);
        return duplicateCiCount > 0;
    }

    public void saveClientUser(String userName, String phoneNumber, String ci, @Nullable String di, LocalDate userBirthday, Gender userGender) {
        clientUserRepository.save(ClientUser.create(userName, phoneNumber, ci, di, userBirthday, userGender));
    }

}
