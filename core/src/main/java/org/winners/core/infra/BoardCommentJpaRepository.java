package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.board.BoardComment;
import org.winners.core.domain.board.BoardCommentRepository;

public interface BoardCommentJpaRepository extends JpaRepository<BoardComment, Long>, BoardCommentRepository {
}
