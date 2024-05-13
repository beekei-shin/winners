package org.winners.app.application.cert.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.winners.app.application.cert.PhoneIdentityCertificationService;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationType;
import org.winners.core.domain.cert.service.CertificationKeyDomainService;
import org.winners.core.domain.cert.service.PhoneIdentityCertificationDomainService;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PhoneIdentityCertificationServiceV1 implements PhoneIdentityCertificationService {

    private final CertificationKeyDomainService certificationKeyDomainService;
    private final PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;

    @Override
    public UUID sendPhoneIdentityOtpNumber(SendOtpNumberParameterDTO sendPhoneIdentityOtpNumberParameter) {
        CertificationKey certificationKey = certificationKeyDomainService.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        return phoneIdentityCertificationDomainService.sendOtpNumber(certificationKey, sendPhoneIdentityOtpNumberParameter);
    }

    @Override
    public void certifyPhoneIdentityOtpNumber(UUID certificationKey, String phoneNumber, String pinNumber) {
        CertificationKey savedCertificationKey = certificationKeyDomainService.getSavedCertificationKey(certificationKey);
        certificationKeyDomainService.possibleCertifyCheck(savedCertificationKey);
        phoneIdentityCertificationDomainService.certifyOtpNumber(savedCertificationKey, phoneNumber, pinNumber);
    }

}
