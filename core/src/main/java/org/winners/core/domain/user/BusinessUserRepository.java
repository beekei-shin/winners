package org.winners.core.domain.user;

public interface BusinessUserRepository {
    long countByPhoneNumber(String phoneNumber);
    BusinessUser save(BusinessUser businessUser);
}
