package org.winners.core.domain.field;

import java.util.List;
import java.util.Set;

public interface FieldRepository {
    List<Field> findAllByOrderBySortOrderAsc();
    List<Field> findByIdIn(Set<Long> ids);
}
