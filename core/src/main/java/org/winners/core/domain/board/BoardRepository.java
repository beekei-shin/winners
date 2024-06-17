package org.winners.core.domain.board;

import java.util.Optional;

public interface BoardRepository {
    Board saveAndFlush(Board board);
    void delete(Board board);
    Optional<Board> findById(long id);
    Optional<Board> findByType(BoardType type);
    long countByTypeAndIdNot(BoardType type, long id);
    long countByType(BoardType type);
}
