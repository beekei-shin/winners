package org.winners.core.domain.user;

import java.util.Optional;

public interface ClientUserRepository {
    ClientUser save(ClientUser clientUser);
    long countByCi(String ci);
    long countByPhoneNumber(String phoneNumber);
    Optional<ClientUser> findById(long userId);
    Optional<ClientUser> findByPhoneNumberAndCi(String ci, String phoneNumber);
}
