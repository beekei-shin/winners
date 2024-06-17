package org.winners.app.application.board.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.winners.app.application.board.dto.UpdatePostParameterDTO;
import org.winners.app.config.ApplicationServiceTest;
import org.winners.core.config.exception.ExceptionMessageType;
import org.winners.core.config.exception.NotAccessDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.config.querydsl.QuerydslRepository;
import org.winners.core.domain.board.*;
import org.winners.core.domain.board.service.BoardCategoryDomainService;
import org.winners.core.domain.board.service.BoardDomainService;
import org.winners.core.domain.board.service.BoardPostDomainService;
import org.winners.core.domain.board.service.dto.SavePostParameterDTO;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

class InquiryBoardPostServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private InquiryBoardPostServiceV1 inquiryBoardPostServiceV1;

    @Mock
    private QuerydslRepository querydslRepository;

    @Mock
    private BoardDomainService boardDomainService;

    @Mock
    private BoardCategoryDomainService boardCategoryDomainService;

    @Mock
    private BoardPostDomainService boardPostDomainService;

    @Mock
    private BoardPostRepository boardPostRepository;

    @Test
    @DisplayName("문의글 등록")
    void savePost() {
        Board board = BoardMock.createBoard(1L, BoardType.INQUIRY);
        given(boardDomainService.getBoardByType(any(BoardType.class))).willReturn(board);

        BoardCategory boardCategory = BoardCategoryMock.createCategory(2L, "카테고리", 1);
        given(boardCategoryDomainService.getCategory(anyLong(), anyLong())).willReturn(boardCategory);

        BoardPost boardPost = BoardPostMock.createPost(3L, 4L, true);
        given(boardPostRepository.save(any(BoardPost.class))).willReturn(boardPost);

        SavePostParameterDTO parameter = SavePostParameterDTO.builder()
            .categoryId(1L)
            .postTitle("제목")
            .postContents("내용")
            .isSecretPost(true)
            .build();
        inquiryBoardPostServiceV1.savePost(1L, parameter);

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
        verify(boardCategoryDomainService).getCategory(board.getId(), parameter.getCategoryId());
        verify(boardPostRepository).save(any(BoardPost.class));
    }

    @Test
    @DisplayName("문의글 등록 - 존재하지 않는 게시판")
    void savePost_notExistBoard() {
        given(boardDomainService.getBoardByType(any(BoardType.class)))
            .willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD));

        Throwable exception = assertThrows(NotExistDataException.class,
            () -> inquiryBoardPostServiceV1.savePost(1L, any(SavePostParameterDTO.class)));

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
    }

    @Test
    @DisplayName("문의글 등록 - 존재하지 않는 게시판 카테고리")
    void savePost_notExistBoardCategory() {
        Board board = BoardMock.createBoard(1L, BoardType.INQUIRY);
        given(boardDomainService.getBoardByType(any(BoardType.class))).willReturn(board);

        given(boardCategoryDomainService.getCategory(anyLong(), anyLong()))
            .willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_CATEGORY));

        SavePostParameterDTO parameter = SavePostParameterDTO.builder()
            .categoryId(1L)
            .build();
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> inquiryBoardPostServiceV1.savePost(1L, parameter));

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
        verify(boardCategoryDomainService).getCategory(board.getId(), parameter.getCategoryId());
    }

    @Test
    @DisplayName("문의글 수정")
    void updatePost() {
        Board board = BoardMock.createBoard(1L, BoardType.INQUIRY);
        given(boardDomainService.getBoardByType(any(BoardType.class))).willReturn(board);

        BoardCategory boardCategory = BoardCategoryMock.createCategory(2L, "카테고리", 1);
        given(boardCategoryDomainService.getCategory(anyLong(), anyLong())).willReturn(boardCategory);

        BoardPost boardPost = BoardPostMock.createPost(3L, 4L, true);
        given(boardPostDomainService.getPost(anyLong())).willReturn(boardPost);

        willDoNothing().given(boardPostDomainService).possibleUpdatePostCheck(any(BoardPost.class), anyLong());

        long userId = 1L;
        long postId = 4L;
        UpdatePostParameterDTO parameter = UpdatePostParameterDTO.builder()
            .categoryId(2L)
            .postTitle("수정 제목")
            .postContents("수정 내용")
            .isSecretPost(false)
            .build();
        inquiryBoardPostServiceV1.updatePost(userId, postId, parameter);

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
        verify(boardCategoryDomainService).getCategory(board.getId(), parameter.getCategoryId());
        verify(boardPostDomainService).getPost(postId);
        verify(boardPostDomainService).possibleUpdatePostCheck(boardPost, userId);
    }

    @Test
    @DisplayName("문의글 수정 - 존재하지 않는 게시판")
    void updatePost_notExistBoard() {
        given(boardDomainService.getBoardByType(any(BoardType.class)))
            .willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD));

        long userId = 1L;
        long postId = 4L;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> inquiryBoardPostServiceV1.updatePost(userId, postId, any(UpdatePostParameterDTO.class)));

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
    }

    @Test
    @DisplayName("문의글 수정 - 존재하지 않는 게시판 카테고리")
    void updatePost_notExistBoardCategory() {
        Board board = BoardMock.createBoard(1L, BoardType.INQUIRY);
        given(boardDomainService.getBoardByType(any(BoardType.class))).willReturn(board);

        given(boardCategoryDomainService.getCategory(anyLong(), anyLong()))
            .willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_CATEGORY));

        long userId = 1L;
        long postId = 4L;
        UpdatePostParameterDTO parameter = UpdatePostParameterDTO.builder()
            .categoryId(2L)
            .build();
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> inquiryBoardPostServiceV1.updatePost(userId, postId, parameter));

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
        verify(boardCategoryDomainService).getCategory(board.getId(), parameter.getCategoryId());
    }

    @Test
    @DisplayName("문의글 수정 - 존재하지 않는 게시글")
    void updatePost_notExistBoardPost() {
        Board board = BoardMock.createBoard(1L, BoardType.INQUIRY);
        given(boardDomainService.getBoardByType(any(BoardType.class))).willReturn(board);

        BoardCategory boardCategory = BoardCategoryMock.createCategory(2L, "카테고리", 1);
        given(boardCategoryDomainService.getCategory(anyLong(), anyLong())).willReturn(boardCategory);

        given(boardPostDomainService.getPost(anyLong())).willThrow(new NotExistDataException(ExceptionMessageType.NOT_EXIST_BOARD_POST));

        long userId = 1L;
        long postId = 4L;
        UpdatePostParameterDTO parameter = UpdatePostParameterDTO.builder()
            .categoryId(2L)
            .postTitle("수정 제목")
            .postContents("수정 내용")
            .isSecretPost(false)
            .build();
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> inquiryBoardPostServiceV1.updatePost(userId, postId, parameter));

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
        verify(boardCategoryDomainService).getCategory(board.getId(), parameter.getCategoryId());
        verify(boardPostDomainService).getPost(postId);
    }

    @Test
    @DisplayName("문의글 수정 - 문의글 수정 불가능")
    void updatePost_impossibleUpdatePost() {
        Board board = BoardMock.createBoard(1L, BoardType.INQUIRY);
        given(boardDomainService.getBoardByType(any(BoardType.class))).willReturn(board);

        BoardCategory boardCategory = BoardCategoryMock.createCategory(2L, "카테고리", 1);
        given(boardCategoryDomainService.getCategory(anyLong(), anyLong())).willReturn(boardCategory);

        BoardPost boardPost = BoardPostMock.createPost(3L, 4L, true);
        given(boardPostDomainService.getPost(anyLong())).willReturn(boardPost);

        willThrow(new NotAccessDataException(ExceptionMessageType.NOT_ACCESS_BOARD_POST))
            .given(boardPostDomainService).possibleUpdatePostCheck(any(BoardPost.class), anyLong());

        long userId = 1L;
        long postId = 4L;
        UpdatePostParameterDTO parameter = UpdatePostParameterDTO.builder()
            .categoryId(2L)
            .postTitle("수정 제목")
            .postContents("수정 내용")
            .isSecretPost(false)
            .build();
        Throwable exception = assertThrows(NotAccessDataException.class,
            () -> inquiryBoardPostServiceV1.updatePost(userId, postId, parameter));

        verify(boardDomainService).getBoardByType(BoardType.INQUIRY);
        verify(boardCategoryDomainService).getCategory(board.getId(), parameter.getCategoryId());
        verify(boardPostDomainService).getPost(postId);
        verify(boardPostDomainService).possibleUpdatePostCheck(boardPost, userId);
    }

}