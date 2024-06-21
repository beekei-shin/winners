package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserRepository;

public interface BusinessUserJpaRepository extends JpaRepository<BusinessUser, Long>, BusinessUserRepository {
}
