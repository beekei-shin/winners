package org.winners.backoffice.application.board.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.backoffice.application.board.dto.UpdateBoardCategoryParameterDTO;
import org.winners.core.domain.board.*;
import org.winners.core.domain.board.service.BoardDomainService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardManageServiceV1 implements BoardManageService {

    private final BoardRepository boardRepository;
    private final BoardDomainService boardDomainService;

    @Override
    public void saveBoard(BoardType boardType, String boardName, LinkedHashSet<String> categoryNames) {
        boardDomainService.duplicateBoardCheck(boardType, boardName);
        Board savedBoard = boardRepository.saveAndFlush(Board.createBoard(boardType, boardName));
        savedBoard.saveCategories(categoryNames);
    }

    @Override
    public void updateBoard(long boardId, String boardName, List<UpdateBoardCategoryParameterDTO> updateCategoryList) {
        Board board = boardDomainService.getBoard(boardId);
        boardDomainService.duplicateBoardCheck(board.getType(), boardName, boardId);
        board.updateBoard(boardName);
        updateCategoryList.forEach(updateCategory ->
            Optional.ofNullable(updateCategory.getCategoryId())
                .ifPresentOrElse(
                    categoryId -> board.updateCategory(updateCategory.getCategoryId(), updateCategory.getCategoryName()),
                    () -> board.saveCategory(updateCategory.getCategoryName())));
    }

    @Override
    public void deleteBoard(long boardId) {
        Board board = boardDomainService.getBoard(boardId);
        boardDomainService.possibleDeleteCheck(board);
        boardDomainService.deleteBoard(board);
    }

}
