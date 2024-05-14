package org.winners.core.domain.cert.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationKeyRepository;
import org.winners.core.domain.cert.CertificationType;

import java.util.UUID;

import static org.winners.core.config.exception.ExceptionMessageType.NOT_EXIST_CERTIFICATION_KEY;

@Service
@Component
@RequiredArgsConstructor
public class CertificationKeyDomainService {

    private final CertificationKeyRepository certificationKeyRepository;

    public CertificationKey createCertificationKey(CertificationType certificationType, int expiredMinute) {
        return certificationKeyRepository.save(CertificationKey.create(certificationType, expiredMinute));
    }

    public CertificationKey getSavedCertificationKey(UUID id) {
        return certificationKeyRepository.findById(id)
            .orElseThrow(() -> new NotExistDataException(NOT_EXIST_CERTIFICATION_KEY));
    }

}
