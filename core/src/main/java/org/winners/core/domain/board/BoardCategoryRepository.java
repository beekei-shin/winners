package org.winners.core.domain.board;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BoardCategoryRepository {

    @Modifying
    @Query("DELETE FROM BoardCategory bc WHERE bc.boardId = :boardId")
    void deleteByBoardId(long boardId);

    @Modifying
    @Query("DELETE FROM BoardCategory bc WHERE bc.id IN :ids")
    void deleteByIdIn(Set<Long> ids);

}
