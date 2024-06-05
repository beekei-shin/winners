package org.winners.core.domain.board;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface BoardCategoryRepository {
    @Transactional
    @Modifying
    @Query("DELETE FROM BoardCategory bc WHERE bc.boardId = :boardId")
    void deleteByBoardId(long boardId);
    BoardCategory saveAndFlush(BoardCategory boardCategory);
    List<BoardCategory> findAll();
}
