package org.winners.backoffice.application.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.winners.core.domain.board.BoardType;

import java.util.LinkedHashSet;

@Service
public interface BoardManageService {
    @Transactional
    void saveBoard(BoardType boardType, String boardName, LinkedHashSet<String> categoryNames);
    @Transactional
    void deleteBoard(long boardId);
}
