package org.winners.core.domain.board;

import java.util.Optional;
import java.util.Set;

public interface BoardPostRepository {
    long countByBoardId(long boardId);
    long countByBoardIdAndCategoryIdIn(long boardId, Set<Long> categoryIds);
    Optional<BoardPost> findById(long id);
    BoardPost save(BoardPost boardPost);
}
