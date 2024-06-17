package org.winners.core.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.exception.CannotProcessedDataException;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.*;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BoardDomainServiceTest extends DomainServiceTest {

    private BoardDomainService boardDomainService;
    private BoardRepository boardRepository;
    private BoardCategoryRepository boardCategoryRepository;
    private BoardPostRepository boardPostRepository;

    @BeforeEach
    public void BeforeEach() {
        this.boardRepository = Mockito.mock(BoardRepository.class);
        this.boardCategoryRepository = Mockito.mock(BoardCategoryRepository.class);
        this.boardPostRepository = Mockito.mock(BoardPostRepository.class);
        this.boardDomainService = new BoardDomainService(boardRepository, boardCategoryRepository, boardPostRepository);
    }

    @Test
    @DisplayName("게시판 조회")
    public void getBoard() {
        Board board = BoardMock.createBoard(1L);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        long boardId = 10L;
        Board returnBoard = boardDomainService.getBoard(boardId);

        assertThat(returnBoard).isEqualTo(board);
        verify(boardRepository).findById(boardId);
    }

    @Test
    @DisplayName("게시판 조회 - 존재하지 않는 게시판")
    public void getBoard_notExistBoard() {
        given(boardRepository.findById(anyLong())).willReturn(Optional.empty());

        long boardId = 10L;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardDomainService.getBoard(boardId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_BOARD.getMessage());
        verify(boardRepository).findById(boardId);
    }

    @Test
    @DisplayName("중복 게시판 확인")
    public void duplicateBoardCheck() {
        BoardType boardType = BoardType.COMMUNITY;
        String boardName = "커뮤니티";
        long boardId = 10;

        given(boardRepository.countByTypeAndName(any(BoardType.class), anyString())).willReturn(0L);
        boardDomainService.duplicateBoardCheck(boardType, boardName);
        verify(boardRepository).countByTypeAndName(boardType, boardName);

        given(boardRepository.countByTypeAndNameAndIdNot(any(BoardType.class), anyString(), anyLong())).willReturn(0L);
        boardDomainService.duplicateBoardCheck(boardType, boardName, boardId);
        verify(boardRepository).countByTypeAndNameAndIdNot(boardType, boardName, boardId);
    }

    @Test
    @DisplayName("중복 게시판 확인 - 중복된 게시판")
    public void duplicateBoardCheck_duplicatedBoard() {
        BoardType boardType = BoardType.COMMUNITY;
        String boardName = "커뮤니티";
        long boardId = 10;

        given(boardRepository.countByTypeAndName(any(BoardType.class), anyString())).willReturn(1L);
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> boardDomainService.duplicateBoardCheck(boardType, boardName));
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_BOARD.getMessage());
        verify(boardRepository).countByTypeAndName(boardType, boardName);


        given(boardRepository.countByTypeAndNameAndIdNot(any(BoardType.class), anyString(), anyLong())).willReturn(1L);
        Throwable exception2 = assertThrows(DuplicatedDataException.class,
            () -> boardDomainService.duplicateBoardCheck(boardType, boardName, boardId));
        assertThat(exception2.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_BOARD.getMessage());
        verify(boardRepository).countByTypeAndNameAndIdNot(boardType, boardName, boardId);
    }

    @Test
    @DisplayName("게시판 삭제 가능 여부 확인")
    public void possibleDeleteCheck() {
        given(boardPostRepository.countByBoardId(anyLong())).willReturn(0L);

        Board board = BoardMock.createBoard(1L);
        boardDomainService.possibleDeleteBoardCheck(board);

        verify(boardPostRepository).countByBoardId(board.getId());
    }

    @Test
    @DisplayName("게시판 삭제 가능 여부 확인 - 삭제 불가능")
    public void possibleDeleteCheck_cannotPossibleDelete() {
        given(boardPostRepository.countByBoardId(anyLong())).willReturn(1L);

        Board board = BoardMock.createBoard(1L);
        Throwable exception = assertThrows(CannotProcessedDataException.class,
            () -> boardDomainService.possibleDeleteBoardCheck(board));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.CANNOT_DELETE_BOARD.getMessage());
        verify(boardPostRepository).countByBoardId(board.getId());
    }

    @Test
    @DisplayName("게시판 삭제")
    public void deleteBoard() {
        willDoNothing().given(boardCategoryRepository).deleteByBoardId(anyLong());
        willDoNothing().given(boardRepository).delete(any(Board.class));

        Board board = BoardMock.createBoard(1L);
        boardDomainService.deleteBoard(board);

        verify(boardCategoryRepository).deleteByBoardId(board.getId());
        verify(boardRepository).delete(board);
    }

    @Test
    @DisplayName("카테고리 삭제 가능 여부 확인")
    public void possibleDeleteCategoryCheck() {
        given(boardPostRepository.countByBoardIdAndCategoryIdIn(anyLong(), anySet())).willReturn(0L);

        Board board = BoardMock.createBoard(1L);
        Set<Long> deleteCategoryIds = Set.of(1L, 2L);
        boardDomainService.possibleDeleteCategoryCheck(board, deleteCategoryIds);

        verify(boardPostRepository).countByBoardIdAndCategoryIdIn(board.getId(), deleteCategoryIds);
    }

    @Test
    @DisplayName("카테고리 삭제 가능 여부 확인 - 삭제 불가능")
    public void possibleDeleteCategoryCheck_cannotPossibleDelete() {
        given(boardPostRepository.countByBoardIdAndCategoryIdIn(anyLong(), anySet())).willReturn(1L);

        Board board = BoardMock.createBoard(1L);
        Set<Long> deleteCategoryIds = Set.of(1L, 2L);
        Throwable exception = assertThrows(CannotProcessedDataException.class,
            () -> boardDomainService.possibleDeleteCategoryCheck(board, deleteCategoryIds));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.CANNOT_DELETE_BOARD_CATEGORY.getMessage());
        verify(boardPostRepository).countByBoardIdAndCategoryIdIn(board.getId(), deleteCategoryIds);
    }

}