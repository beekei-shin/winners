package org.winners.app.application.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.winners.app.application.user.ClientUserJobService;
import org.winners.core.domain.user.service.ClientUserDomainService;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClientUserJobServiceV1 implements ClientUserJobService {

    private final ClientUserDomainService clientUserDomainService;

    @Override
    public void saveClientUserJob(long userId, Set<Long> fieldIds) {
        clientUserDomainService.saveClientUserJob(userId, fieldIds);
    }

}
