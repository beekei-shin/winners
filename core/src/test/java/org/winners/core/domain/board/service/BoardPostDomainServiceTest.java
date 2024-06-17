package org.winners.core.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.winners.core.config.DomainServiceTest;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotAccessDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.BoardPost;
import org.winners.core.domain.board.BoardPostMock;
import org.winners.core.domain.board.BoardPostRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BoardPostDomainServiceTest extends DomainServiceTest {

    private BoardPostDomainService boardPostDomainService;
    private BoardPostRepository boardPostRepository;

    @BeforeEach
    public void BeforeEach() {
        this.boardPostRepository = Mockito.mock(BoardPostRepository.class);
        this.boardPostDomainService = new BoardPostDomainService(boardPostRepository);
    }


    @Test
    @DisplayName("게시글 열람 가능 여부 확인")
    void possibleViewPostCheck() {
        BoardPost boardPost1 = BoardPostMock.createPost(1L, 1L, true);
        boardPostDomainService.possibleViewPostCheck(boardPost1, 1L);

        BoardPost boardPost2 = BoardPostMock.createPost(1L, 1L, false);
        boardPostDomainService.possibleViewPostCheck(boardPost2, 2L);

        Throwable exception = assertThrows(NotAccessDataException.class,
            () -> boardPostDomainService.possibleViewPostCheck(boardPost1, 2L));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_ACCESS_BOARD_POST.getMessage());
    }

    @Test
    @DisplayName("게시글 수정 가능 여부 확인")
    void possibleUpdatePostCheck() {
        BoardPost boardPost = BoardPostMock.createPost(1L, 1L, true);
        boardPostDomainService.possibleUpdatePostCheck(boardPost, 1L);

        Throwable exception = assertThrows(NotAccessDataException.class,
            () -> boardPostDomainService.possibleUpdatePostCheck(boardPost, 2L));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_ACCESS_BOARD_POST.getMessage());
    }

    @Test
    @DisplayName("게시글 조회")
    void getPost() {
        BoardPost boardPost = BoardPostMock.createPost(1L, 1L, true);
        given(boardPostRepository.findById(anyLong())).willReturn(Optional.of(boardPost));

        long postId = 1L;
        BoardPost returnBoardPost = boardPostDomainService.getPost(postId);

        assertThat(returnBoardPost).isEqualTo(boardPost);
        verify(boardPostRepository).findById(postId);
    }

    @Test
    @DisplayName("게시글 조회 - 존재하지 않는 게시글")
    void getPost_notExistBoardPost() {
        given(boardPostRepository.findById(anyLong())).willReturn(Optional.empty());

        long postId = 1L;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardPostDomainService.getPost(postId));

        assertThat(exception.getMessage()).isEqualTo(ExceptionMessageType.NOT_EXIST_BOARD_POST.getMessage());
        verify(boardPostRepository).findById(postId);
    }

}