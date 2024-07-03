package com.winners.appApi.application.user.impl;

import com.winners.appApi.application.user.ClientUserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.winners.appApi.application.user.dto.SignInClientUserResultDTO;
import com.winners.appApi.application.user.dto.SignUpClientUserResultDTO;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.auth.service.AuthDomainService;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.service.CertificationKeyDomainService;
import org.winners.core.domain.cert.service.PhoneIdentityCertificationDomainService;
import org.winners.core.domain.cert.service.dto.CertificationInfoDTO;
import org.winners.core.domain.common.DeviceOs;
import org.winners.core.domain.user.ClientUser;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.ClientUserDomainService;
import org.winners.core.domain.user.service.dto.SaveClientUserParameterDTO;

import java.util.UUID;

import static org.winners.core.config.exception.ExceptionMessageType.NOT_EXIST_USER;

@Component
@RequiredArgsConstructor
public class ClientUserSignServiceV1 implements ClientUserSignService {

    private final ClientUserRepository clientUserRepository;
    private final ClientUserDomainService clientUserDomainService;
    private final CertificationKeyDomainService certificationKeyDomainService;
    private final PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;
    private final AuthDomainService authDomainService;

    @Override
    public SignUpClientUserResultDTO signUp(UUID certificationKey, DeviceOs deviceOs, String deviceToken) {
        CertificationKey savedCertificationKey = certificationKeyDomainService.getSavedCertificationKey(certificationKey);
        CertificationInfoDTO certificationInfo = phoneIdentityCertificationDomainService.getCertificationInfo(savedCertificationKey);
        savedCertificationKey.possibleUseCheck();
        savedCertificationKey.use();

        clientUserDomainService.duplicatePhoneNumberCheck(certificationInfo.getPhoneNumber());
        clientUserDomainService.duplicateCiCheck(certificationInfo.getCi());

        ClientUser clientUser = clientUserDomainService.saveClientUser(SaveClientUserParameterDTO.builder()
                .name(certificationInfo.getName())
                .phoneNumber(certificationInfo.getPhoneNumber())
                .ci(certificationInfo.getCi())
                .di(certificationInfo.getDi())
                .birthday(certificationInfo.getBirthday())
                .gender(certificationInfo.getGender())
            .build());

        AuthTokenDTO authToken = authDomainService.createClientUserAuthToken(clientUser.getId(), deviceOs, deviceToken);

        return SignUpClientUserResultDTO.success(clientUser, authToken);
    }

    @Override
    public SignInClientUserResultDTO signIn(UUID certificationKey, DeviceOs deviceOs, String deviceToken) {
        CertificationKey savedCertificationKey = certificationKeyDomainService.getSavedCertificationKey(certificationKey);
        CertificationInfoDTO certificationInfo = phoneIdentityCertificationDomainService.getCertificationInfo(savedCertificationKey);
        savedCertificationKey.possibleUseCheck();
        savedCertificationKey.use();

        return clientUserRepository.findByPhoneNumberAndCi(certificationInfo.getPhoneNumber(), certificationInfo.getCi())
            .map(clientUser -> {
                clientUser.accessUserCheck();
                AuthTokenDTO authToken = authDomainService.createClientUserAuthToken(clientUser.getId(), deviceOs, deviceToken);
                return SignInClientUserResultDTO.success(clientUser, authToken);
            })
            .orElseThrow(() -> new NotExistDataException(NOT_EXIST_USER));
    }

}
