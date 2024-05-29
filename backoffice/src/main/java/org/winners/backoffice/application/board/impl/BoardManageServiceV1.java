package org.winners.backoffice.application.board.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.core.config.exception.CannotProcessedDataException;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.*;

import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class BoardManageServiceV1 implements BoardManageService {

    private final BoardRepository boardRepository;
    private final BoardPostRepository boardPostRepository;
    private final BoardCategoryRepository boardCategoryRepository;

    @Override
    public void saveBoard(BoardType boardType, String boardName, LinkedHashSet<String> categoryNames) {
        if (boardRepository.countByTypeAndName(boardType, boardName) > 0)
            throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_BOARD);

        Board savedBoard = boardRepository.saveAndFlush(Board.create(boardType, boardName));
        savedBoard.addCategories(categoryNames);
    }

    @Override
    public void deleteBoard(long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD));

        if (boardPostRepository.countByBoardId(boardId) > 0)
            throw new CannotProcessedDataException(ExceptionMessageType.CANNOT_DELETE_BOARD);

        boardCategoryRepository.deleteByBoardId(board.getId());
        boardRepository.delete(board);
    }

}
