package org.winners.app.application.cert;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.util.UUID;

@Service
public interface PhoneIdentityCertificationService {

    @Transactional
    UUID sendPhoneIdentityOtpNumber(SendOtpNumberParameterDTO sendPhoneIdentityOtpNumberParameter);

    @Transactional
    void certifyPhoneIdentityOtpNumber(UUID certificationKey, String phoneNumber, String optNumber);

}
