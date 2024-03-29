package org.winners.app.application.user.impl;

import lombok.RequiredArgsConstructor;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.dto.SignInClientUserResultDTO;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.auth.service.AuthDomainService;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.service.CertificationKeyDomainService;
import org.winners.core.domain.cert.service.PhoneIdentityCertificationDomainService;
import org.winners.core.domain.cert.service.dto.CertificationInfoDTO;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.ClientUserDomainService;
import org.winners.core.domain.user.service.dto.SaveClientUserParameterDTO;

import java.util.UUID;

import static org.winners.core.config.exception.ExceptionMessageType.NOT_EXIST_USER;

@RequiredArgsConstructor
public class ClientUserSignServiceV1 implements ClientUserSignService {

    private final ClientUserRepository clientUserRepository;
    private final ClientUserDomainService clientUserDomainService;
    private final CertificationKeyDomainService certificationKeyDomainService;
    private final PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;
    private final AuthDomainService authDomainService;

    @Override
    public void signUp(UUID certificationKey) {
        final CertificationKey savedCertificationKey = certificationKeyDomainService.getSavedCertificationKey(certificationKey);
        certificationKeyDomainService.possibleUseCheck(savedCertificationKey);

        final CertificationInfoDTO certificationInfo = phoneIdentityCertificationDomainService.getCertificationInfo(savedCertificationKey);
        savedCertificationKey.use();

        clientUserDomainService.duplicatePhoneNumberCheck(certificationInfo.getPhoneNumber());
        clientUserDomainService.duplicateCiCheck(certificationInfo.getCi());
        clientUserDomainService.saveClientUser(SaveClientUserParameterDTO.builder()
                .name(certificationInfo.getName())
                .phoneNumber(certificationInfo.getPhoneNumber())
                .ci(certificationInfo.getCi())
                .di(certificationInfo.getDi())
                .birthday(certificationInfo.getBirthday())
                .gender(certificationInfo.getGender())
            .build());
    }

    @Override
    public SignInClientUserResultDTO signIn(UUID certificationKey) {
        final CertificationKey savedCertificationKey = certificationKeyDomainService.getSavedCertificationKey(certificationKey);
        certificationKeyDomainService.possibleUseCheck(savedCertificationKey);

        final CertificationInfoDTO certificationInfo = phoneIdentityCertificationDomainService.getCertificationInfo(savedCertificationKey);
        savedCertificationKey.use();

        return clientUserRepository.findByPhoneNumberAndCi(certificationInfo.getPhoneNumber(), certificationInfo.getCi())
            .filter(clientUserDomainService::accessClientUserCheck)
            .map(clientUser -> {
                AuthTokenDTO authToken = authDomainService.createClientUserAuthToken(clientUser.getId());
                return SignInClientUserResultDTO.success(clientUser.getId(), authToken.getAccessToken(), authToken.getRefreshToken());
            })
            .orElseThrow(() -> new NotExistDataException(NOT_EXIST_USER));
    }

}
