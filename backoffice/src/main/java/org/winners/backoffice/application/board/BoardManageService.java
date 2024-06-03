package org.winners.backoffice.application.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winners.core.domain.board.BoardType;
import org.winners.core.domain.board.service.dto.SaveAndUpdateBoardCategoryParameterDTO;

import java.util.LinkedHashSet;
import java.util.List;

@Service
public interface BoardManageService {
    @Transactional
    void saveBoard(BoardType boardType, String boardName, LinkedHashSet<String> categoryNames);

    @Transactional
    void updateBoard(long boardId, String boardName, List<SaveAndUpdateBoardCategoryParameterDTO> updateCategoryList);

    @Transactional
    void deleteBoard(long boardId);

}
