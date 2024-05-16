package org.winners.core.domain.cert.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.InvalidDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistory;
import org.winners.core.domain.cert.PhoneIdentityCertificationHistoryRepository;
import org.winners.core.domain.cert.service.dto.CertificationInfoDTO;
import org.winners.core.domain.cert.service.dto.SendOtpNumberParameterDTO;

import java.util.UUID;

import static org.winners.core.config.exception.ExceptionMessageType.INVALID_OTP_NUMBER;
import static org.winners.core.config.exception.ExceptionMessageType.NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY;

@Service
@RequiredArgsConstructor
public class PhoneIdentityCertificationDomainService {

    private final PasswordEncoder passwordEncoder;
    private final PhoneIdentityCertificationHistoryRepository phoneIdentityCertificationHistoryRepository;

    public UUID sendOtpNumber(CertificationKey certificationKey, SendOtpNumberParameterDTO parameter) {
        String defaultOtpNumber = "000000";
        String encryptOtpNumber = passwordEncoder.encode(defaultOtpNumber);
        PhoneIdentityCertificationHistory certHistory = phoneIdentityCertificationHistoryRepository.save(PhoneIdentityCertificationHistory.create(
            certificationKey,
            parameter.getName(),
            parameter.getBirthday(),
            parameter.getGender(),
            parameter.getMobileCarrier(),
            parameter.getPhoneNumber(),
            encryptOtpNumber
        ));
        return certHistory.getCertificationKey().getId();
    }

    public void certifyOtpNumber(CertificationKey certificationKey, String phoneNumber, String optNumber) {
        phoneIdentityCertificationHistoryRepository.findByCertificationKeyAndPhoneNumber(certificationKey, phoneNumber)
            .ifPresentOrElse(
                authHistory -> {
                    if (!passwordEncoder.matches(optNumber, authHistory.getOtpNumber()))
                        throw new InvalidDataException(INVALID_OTP_NUMBER);
                    authHistory.certify("ci", "di");
                },
                () -> { throw new NotExistDataException(NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY); });
    }

    public CertificationInfoDTO getCertificationInfo(CertificationKey certificationKey) {
        return phoneIdentityCertificationHistoryRepository.findByCertificationKey(certificationKey)
            .map(CertificationInfoDTO::convert)
            .orElseThrow(() -> new NotExistDataException(NOT_EXIST_PHONE_IDENTITY_CERTIFICATION_HISTORY));
    }

}
