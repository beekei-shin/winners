package org.winners.backoffice.application.board.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.core.domain.board.Board;
import org.winners.core.domain.board.BoardCategory;
import org.winners.core.domain.board.BoardRepository;
import org.winners.core.domain.board.BoardType;
import org.winners.core.domain.board.service.BoardDomainService;
import org.winners.core.domain.board.service.dto.SaveAndUpdateBoardCategoryParameterDTO;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void updateBoard(long boardId, String boardName, List<SaveAndUpdateBoardCategoryParameterDTO> updateCategoryList) {
        Board board = boardDomainService.getBoard(boardId);

        Set<Long> updateCategoryIds = updateCategoryList.stream()
            .map(SaveAndUpdateBoardCategoryParameterDTO::getCategoryId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        Set<Long> deleteCategoryIds = board.getCategoryList().stream()
            .map(BoardCategory::getId)
            .filter(categoryId -> !updateCategoryIds.contains(categoryId))
            .collect(Collectors.toSet());

        boardDomainService.duplicateBoardCheck(board.getType(), boardName, boardId);
        boardDomainService.possibleDeleteCategoryCheck(board, deleteCategoryIds);

        board.updateBoard(boardName);
        board.deleteCategories(deleteCategoryIds);
        board.saveAndUpdateCategories(updateCategoryList);
    }

    @Override
    public void deleteBoard(long boardId) {
        Board board = boardDomainService.getBoard(boardId);
        boardDomainService.possibleDeleteBoardCheck(board);
        boardDomainService.deleteBoard(board);
    }

}
