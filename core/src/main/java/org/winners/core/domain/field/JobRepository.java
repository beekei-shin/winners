package org.winners.core.domain.field;

import java.util.List;
import java.util.Set;

public interface JobRepository {
    List<Job> findByIdIn(Set<Long> ids);
}
