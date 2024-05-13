package org.winners.core.domain.cert.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.AlreadyProcessedDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.exception.UnprocessedDataException;
import org.winners.core.domain.cert.CertificationKey;
import org.winners.core.domain.cert.CertificationKeyRepository;
import org.winners.core.domain.cert.CertificationType;

import java.util.UUID;

import static org.winners.core.config.exception.ExceptionMessageType.*;

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
            .orElseThrow(() -> new NotExistDataException(NOT_EXIST_AUTHENTICATION_KEY));
    }

    public void certifiedCheck(CertificationKey certificationKey) {
        if (certificationKey.isCertified())
            throw new AlreadyProcessedDataException(ALREADY_CERTIFIED_AUTHENTICATION_KEY);
    }

    public void notCertifiedCheck(CertificationKey certificationKey) {
        if (!certificationKey.isCertified())
            throw new UnprocessedDataException(NOT_CERTIFIED_AUTHENTICATION_KEY);
    }

    public void usedCheck(CertificationKey certificationKey) {
        if (certificationKey.isUsed())
            throw new AlreadyProcessedDataException(ALREADY_USED_AUTHENTICATION_KEY);
    }

    public void expiredCheck(CertificationKey certificationKey) {
        if (certificationKey.isExpired())
            throw new AlreadyProcessedDataException(EXPIRED_AUTHENTICATION_KEY);
    }

    public void possibleCertifyCheck(CertificationKey certificationKey) {
        this.expiredCheck(certificationKey);
        this.certifiedCheck(certificationKey);
        this.usedCheck(certificationKey);
    }

    public void possibleUseCheck(CertificationKey certificationKey) {
        this.expiredCheck(certificationKey);
        this.notCertifiedCheck(certificationKey);
        this.usedCheck(certificationKey);
    }

}
