package org.winners.core.domain.board;

import java.util.Set;

public interface BoardPostRepository {
    long countByBoardId(long boardId);
    long countByBoardIdAndCategoryIdIn(long boardId, Set<Long> categoryIds);
}
