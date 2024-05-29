package org.winners.backoffice.application.board.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.winners.backoffice.application.ApplicationServiceTest;
import org.winners.core.config.exception.CannotProcessedDataException;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.*;

import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BoardManageServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private BoardManageServiceV1 boardManageServiceV1;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardPostRepository boardPostRepository;

    @Mock
    private BoardCategoryRepository boardCategoryRepository;

    @Test
    @DisplayName("게시판 등록")
    void saveBoard() {
        given(boardRepository.countByTypeAndName(any(BoardType.class), anyString())).willReturn(0L);
        given(boardRepository.saveAndFlush(any(Board.class))).willReturn(BoardMock.craeteBoard());

        BoardType boardType = BoardType.NOTICE;
        String boardName = "공지사항";
        LinkedHashSet<String> categoryNames = new LinkedHashSet<>() {{ add("공지1"); add("공지2"); }};
        boardManageServiceV1.saveBoard(boardType, boardName, categoryNames);

        verify(boardRepository).countByTypeAndName(boardType, boardName);
    }

    @Test
    @DisplayName("게시판 등록 - 중복된 게시판")
    void saveBoard_duplicatedBoard() {
        given(boardRepository.countByTypeAndName(any(BoardType.class), anyString())).willReturn(1L);

        BoardType boardType = BoardType.NOTICE;
        String boardName = "공지사항";
        LinkedHashSet<String> categoryNames = new LinkedHashSet<>() {{ add("공지1"); add("공지2"); }};
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> boardManageServiceV1.saveBoard(boardType, boardName, categoryNames));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.DUPLICATED_BOARD.getMessage());
        verify(boardRepository).countByTypeAndName(boardType, boardName);
    }

    @Test
    @DisplayName("게시판 삭제")
    void deleteBoard() {
        Board board = BoardMock.craeteBoard();
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        given(boardPostRepository.countByBoardId(anyLong())).willReturn(0L);
        willDoNothing().given(boardCategoryRepository).deleteByBoardId(anyLong());
        willDoNothing().given(boardRepository).delete(any(Board.class));

        long boardId = 10;
        boardManageServiceV1.deleteBoard(boardId);

        verify(boardRepository).findById(boardId);
        verify(boardPostRepository).countByBoardId(boardId);
    }

    @Test
    @DisplayName("게시판 삭제 - 존재하지 않는 게시판")
    void deleteBoard_notExistBoard() {
        given(boardRepository.findById(anyLong())).willReturn(Optional.empty());

        long boardId = 10;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardManageServiceV1.deleteBoard(boardId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_BOARD.getMessage());
        verify(boardRepository).findById(boardId);
    }

    @Test
    @DisplayName("게시판 삭제 - 게시글이 등록된 게시판")
    void deleteBoard_savedBoardPost() {
        Board board = BoardMock.craeteBoard();
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        given(boardPostRepository.countByBoardId(anyLong())).willReturn(1L);

        long boardId = 10;
        Throwable exception = assertThrows(CannotProcessedDataException.class,
            () -> boardManageServiceV1.deleteBoard(boardId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.CANNOT_DELETE_BOARD.getMessage());
        verify(boardRepository).findById(boardId);
    }

}