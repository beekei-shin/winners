package org.winners.core.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.winners.core.config.exception.CannotProcessedDataException;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.*;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardDomainService {

    public static final int BOARD_CATEGORY_MAX_COUNT = 10;

    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardPostRepository boardPostRepository;

    public Board getBoard(long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD));
    }

    public Board getBoardByType(BoardType boardType) {
        return boardRepository.findByType(boardType)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD));
    }

    public void duplicateBoardCheck(BoardType boardType) {
        this.duplicateBoardCheck(boardType ,null);
    }

    public void duplicateBoardCheck(BoardType boardType, Long boardId) {
        Optional.ofNullable(boardId)
            .ifPresentOrElse(id -> {
                if (boardRepository.countByTypeAndIdNot(boardType, id) > 0)
                    throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_BOARD);
            }, () -> {
                if (boardRepository.countByType(boardType) > 0)
                    throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_BOARD);
            });
    }

    public void possibleDeleteBoardCheck(Board board) {
        if (boardPostRepository.countByBoardId(board.getId()) > 0)
            throw new CannotProcessedDataException(ExceptionMessageType.CANNOT_DELETE_BOARD);
    }

    public void deleteBoard(Board board) {
        boardCategoryRepository.deleteByBoardId(board.getId());
        boardRepository.delete(board);
    }

    public void possibleDeleteCategoryCheck(Board board, Set<Long> deleteCategoryIds) {
        if (boardPostRepository.countByBoardIdAndCategoryIdIn(board.getId(), deleteCategoryIds) > 0)
            throw new CannotProcessedDataException(ExceptionMessageType.CANNOT_DELETE_BOARD_CATEGORY);
    }

}
