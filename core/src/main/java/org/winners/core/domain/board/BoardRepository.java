package org.winners.core.domain.board;

import java.util.Optional;

public interface BoardRepository {
    Board saveAndFlush(Board board);
    void delete(Board board);
    long countByTypeAndName(BoardType type, String name);
    Optional<Board> findById(long id);
}
