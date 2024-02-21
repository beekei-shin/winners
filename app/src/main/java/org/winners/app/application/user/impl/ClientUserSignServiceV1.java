package org.winners.app.application.user.impl;


import lombok.RequiredArgsConstructor;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.app.application.user.dto.SignUpClientUserParameterDTO;
import org.winners.app.application.user.dto.SignUpClientUserResultDTO;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.auth.service.AuthenticationKeyDomainService;
import org.winners.core.domain.auth.service.PhoneIdentityAuthenticationDomainService;
import org.winners.core.domain.auth.service.dto.PhoneIdentityAuthenticationInfoDTO;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.ClientUserDomainService;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ClientUserSignServiceV1 implements ClientUserSignService {

    private final ClientUserRepository clientUserRepository;
    private final ClientUserDomainService clientUserDomainService;
    private final AuthenticationKeyDomainService authenticationKeyDomainService;
    private final PhoneIdentityAuthenticationDomainService phoneIdentityAuthenticationDomainService;

    @Override
    public SignUpClientUserResultDTO signUpClientUser(UUID authenticationKey) {
        final PhoneIdentityAuthenticationInfoDTO authenticationInfo = phoneIdentityAuthenticationDomainService.getPhoneIdentityAuthenticationInfo(authenticationKey);
        authenticationKeyDomainService.useAuthenticationKey(authenticationKey);

        if (clientUserDomainService.duplicatePhoneNumberCheck(authenticationInfo.getPhoneNumber()))
            return SignUpClientUserResultDTO.duplicatedPhoneNumber();

        if (clientUserDomainService.duplicateCiCheck(authenticationInfo.getCi()))
            return SignUpClientUserResultDTO.duplicatedCi();

        clientUserDomainService.saveClientUser(authenticationInfo.getName(), authenticationInfo.getPhoneNumber(), authenticationInfo.getCi(), authenticationInfo.getDi(), authenticationInfo.getBirthday(), authenticationInfo.getGender());
        return SignUpClientUserResultDTO.successSignIn();
    }

    @Override
    public SignInClientUserResultDTO signInClientUser(UUID authenticationKey) {
        final PhoneIdentityAuthenticationInfoDTO authenticationInfo = phoneIdentityAuthenticationDomainService.getPhoneIdentityAuthenticationInfo(authenticationKey);
        authenticationKeyDomainService.useAuthenticationKey(authenticationKey);

        final Optional<ClientUser> clientUserOpt = clientUserRepository.findByPhoneNumberAndCi(authenticationInfo.getPhoneNumber(), authenticationInfo.getCi());
        return clientUserOpt
            .map(clientUser -> {
                if (clientUser.isBlockUser())
                    return SignInClientUserResultDTO.blockUser();
                else
                    return SignInClientUserResultDTO.successSignIn();
            }).orElseGet(SignInClientUserResultDTO::notExistUser);
    }

}
