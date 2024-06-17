package org.winners.core.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.BoardCategory;
import org.winners.core.domain.board.BoardCategoryMock;
import org.winners.core.domain.board.BoardCategoryRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BoardCategoryDomainServiceTest extends DomainServiceTest {

    private BoardCategoryDomainService boardCategoryDomainService;

    private BoardCategoryRepository boardCategoryRepository;

    @BeforeEach
    public void BeforeEach() {
        this.boardCategoryRepository = Mockito.mock(BoardCategoryRepository.class);
        this.boardCategoryDomainService = new BoardCategoryDomainService(boardCategoryRepository);
    }

    @Test
    @DisplayName("카테고리 조회")
    public void getCategory() {
        BoardCategory boardCategory = BoardCategoryMock.createCategory(1L, "카테고리", 1);
        given(boardCategoryRepository.findByIdAndBoardId(anyLong(), anyLong())).willReturn(Optional.of(boardCategory));

        long boardId = 1L;
        long categoryId = 2L;
        BoardCategory returnBoardCategory = boardCategoryDomainService.getCategory(boardId, categoryId);

        assertThat(returnBoardCategory).isEqualTo(boardCategory);
        verify(boardCategoryRepository).findByIdAndBoardId(categoryId, boardId);
    }

    @Test
    @DisplayName("카테고리 조회 - 존재하지 않는 카테고리")
    public void getCategory_notExistBoardCategory() {
        given(boardCategoryRepository.findByIdAndBoardId(anyLong(), anyLong())).willReturn(Optional.empty());

        long boardId = 1L;
        long categoryId = 2L;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardCategoryDomainService.getCategory(boardId, categoryId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_BOARD_CATEGORY.getMessage());
        verify(boardCategoryRepository).findByIdAndBoardId(categoryId, boardId);
    }

}