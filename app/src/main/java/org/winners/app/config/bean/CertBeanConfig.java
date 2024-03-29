package org.winners.app.config.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winners.app.application.cert.PhoneIdentityCertificationService;
import org.winners.app.application.cert.impl.PhoneIdentityCertificationServiceV1;
import org.winners.core.domain.cert.service.CertificationKeyDomainService;
import org.winners.core.domain.cert.service.PhoneIdentityCertificationDomainService;

@ComponentScan("org.winners.core.domain")
@Configuration
@RequiredArgsConstructor
public class CertBeanConfig {

    private final CertificationKeyDomainService certificationKeyDomainService;
    private final PhoneIdentityCertificationDomainService phoneIdentityCertificationDomainService;

    @Bean(name = "PhoneIdentityCertificationServiceV1")
    public PhoneIdentityCertificationService phoneIdentityCertificationService() {
        return new PhoneIdentityCertificationServiceV1(certificationKeyDomainService, phoneIdentityCertificationDomainService);
    }

}
