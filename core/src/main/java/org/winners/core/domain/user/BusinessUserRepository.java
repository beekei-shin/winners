package org.winners.core.domain.user;

import java.util.Optional;

public interface BusinessUserRepository {
    long countByPhoneNumber(String phoneNumber);
    BusinessUser save(BusinessUser businessUser);
    Optional<BusinessUser> findById(long id);
    Optional<BusinessUser> findByPhoneNumber(String phoneNumber);
}
