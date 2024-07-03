package com.winners.appApi.application.cert.impl;

import com.winners.appApi.application.cert.PhoneIdentityCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
    public UUID sendPhoneIdentityOtpNumber(SendOtpNumberParameterDTO sendOtpNumberParameter) {
        CertificationKey certificationKey = certificationKeyDomainService.createCertificationKey(CertificationType.PHONE_IDENTITY, 5);
        return phoneIdentityCertificationDomainService.sendOtpNumber(certificationKey, sendOtpNumberParameter);
    }

    @Override
    public void certifyPhoneIdentityOtpNumber(UUID certificationKey, String phoneNumber, String optNumber) {
        CertificationKey savedCertificationKey = certificationKeyDomainService.getSavedCertificationKey(certificationKey);
        phoneIdentityCertificationDomainService.certifyOtpNumber(savedCertificationKey, phoneNumber, optNumber);
        savedCertificationKey.possibleCertifyCheck();
        savedCertificationKey.certify();
    }

}
