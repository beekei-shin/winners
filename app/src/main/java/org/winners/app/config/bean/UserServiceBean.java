package org.winners.app.config.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winners.app.application.user.ClientUserSignService;
import org.winners.app.application.user.impl.ClientUserSignServiceV1;
import org.winners.core.domain.auth.service.AuthenticationKeyDomainService;
import org.winners.core.domain.auth.service.PhoneIdentityAuthenticationDomainService;
import org.winners.core.domain.user.ClientUserRepository;
import org.winners.core.domain.user.service.ClientUserDomainService;

@ComponentScan("org.winners.core.domain")
@Configuration
@RequiredArgsConstructor
public class UserServiceBean {

    private final ClientUserRepository clientUserRepository;
    private final ClientUserDomainService clientUserDomainService;
    private final AuthenticationKeyDomainService authenticationKeyDomainService;
    private final PhoneIdentityAuthenticationDomainService phoneIdentityAuthenticationDomainService;

    @Bean(name = "ClientUserSignServiceV1")
    public ClientUserSignService clientUserSignService() {
        return new ClientUserSignServiceV1(
            clientUserRepository,
            clientUserDomainService,
            authenticationKeyDomainService,
            phoneIdentityAuthenticationDomainService);
    }

}
