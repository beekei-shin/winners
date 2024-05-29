package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.board.BoardCategory;
import org.winners.core.domain.board.BoardCategoryRepository;

public interface BoardCategoryJpaRepository extends JpaRepository<BoardCategory, Long>, BoardCategoryRepository {
}
