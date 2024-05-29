package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.board.Board;
import org.winners.core.domain.board.BoardRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long>, BoardRepository {
}
