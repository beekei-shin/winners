package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.field.Field;
import org.winners.core.domain.field.FieldRepository;

public interface FieldJpaRepository extends JpaRepository<Field, Long>, FieldRepository {
}
