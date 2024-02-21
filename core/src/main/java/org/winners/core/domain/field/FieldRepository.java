package org.winners.core.domain.field;

import java.util.List;

public interface FieldRepository {
    List<Field> findAllByOrderBySortOrderAsc();
}
