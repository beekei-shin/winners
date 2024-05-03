package org.winners.app.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public interface ClientUserJobService {

    @Transactional
    void saveClientUserJob(long userId, Set<Long> fieldIds);

}
