package org.winners.core.domain.board;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardCategoryRepository {

    @Modifying
    @Query("DELETE FROM BoardCategory bc WHERE bc.boardId = :boardId")
    void deleteByBoardId(long boardId);

}
