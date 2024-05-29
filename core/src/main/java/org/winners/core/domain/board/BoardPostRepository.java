package org.winners.core.domain.board;

public interface BoardPostRepository {
    long countByBoardId(long boardId);
}
