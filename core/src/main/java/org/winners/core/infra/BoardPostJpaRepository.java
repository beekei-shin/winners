package org.winners.core.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.winners.core.domain.board.BoardPost;
import org.winners.core.domain.board.BoardPostRepository;

public interface BoardPostJpaRepository extends JpaRepository<BoardPost, Long>, BoardPostRepository {
}
