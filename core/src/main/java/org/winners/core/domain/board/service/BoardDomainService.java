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
    private final BoardPostRepository boardPostRepository;
    private final BoardCategoryRepository boardCategoryRepository;

    public Board getBoard(long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD));
    }

    public void duplicateBoardCheck(BoardType boardType, String boardName) {
        this.duplicateBoardCheck(boardType, boardName ,null);
    }

    public void duplicateBoardCheck(BoardType boardType, String boardName, Long boardId) {
        Optional.ofNullable(boardId)
            .ifPresentOrElse(id -> {
                if (boardRepository.countByTypeAndNameAndIdNot(boardType, boardName, id) > 0)
                    throw new DuplicatedDataException(ExceptionMessageType.DUPLICATED_BOARD);
            }, () -> {
                if (boardRepository.countByTypeAndName(boardType, boardName) > 0)
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