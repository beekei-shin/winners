package org.winners.backoffice.application.board.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.winners.backoffice.application.board.dto.BoardInfoDTO;
import org.winners.backoffice.config.ApplicationServiceTest;
import org.winners.core.config.exception.CannotProcessedDataException;
import org.winners.core.config.exception.DuplicatedDataException;
import org.winners.core.config.exception.NotExistDataException;
import org.winners.core.domain.board.*;
import org.winners.core.domain.board.service.BoardDomainService;
import org.winners.core.domain.board.service.dto.SaveAndUpdateBoardCategoryParameterDTO;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

class BoardManageServiceV1Test extends ApplicationServiceTest {

    @SpyBean
    @InjectMocks
    private BoardManageServiceV1 boardManageServiceV1;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardDomainService boardDomainService;

    @Test
    @DisplayName("게시판 등록")
    void saveBoard() {
        Board board = BoardMock.createBoard(1L);
        willDoNothing().given(boardDomainService).duplicateBoardCheck(any(BoardType.class), anyString());
        given(boardRepository.saveAndFlush(any(Board.class))).willReturn(board);

        BoardType boardType = BoardType.NOTICE;
        String boardName = "공지사항";
        LinkedHashSet<String> categoryNames = new LinkedHashSet<>() {{ add("공지1"); add("공지2"); }};
        boardManageServiceV1.saveBoard(boardType, boardName, categoryNames);

        List<BoardCategory> categoryList = board.getCategoryList();
        List<String> categoryNameList = new ArrayList<>(categoryNames);
        assertThat(categoryList.size()).isEqualTo(categoryNames.size());
        IntStream.range(0, categoryList.size()).forEach(idx -> {
            BoardCategory boardCategory = categoryList.get(idx);
            assertThat(boardCategory.getName()).isEqualTo(categoryNameList.get(idx));
            assertThat(boardCategory.getOrderNumber()).isEqualTo(idx + 1);
        });
        verify(boardDomainService).duplicateBoardCheck(boardType, boardName);
    }

    @Test
    @DisplayName("게시판 등록 - 중복된 게시판")
    void saveBoard_duplicatedBoard() {
        willThrow(DuplicatedDataException.class).given(boardDomainService).duplicateBoardCheck(any(BoardType.class), anyString());

        BoardType boardType = BoardType.NOTICE;
        String boardName = "공지사항";
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> boardManageServiceV1.saveBoard(boardType, boardName, new LinkedHashSet<>()));

        verify(boardDomainService).duplicateBoardCheck(boardType, boardName);
    }

    @Test
    @DisplayName("게시판 수정")
    void updateBoard() {
        List<BoardCategory> savedCategoryList = new ArrayList<>() {{
            add(BoardCategoryMock.createCategory(1L, "카테고리1", 1));
            add(BoardCategoryMock.createCategory(2L, "카테고리2", 2));
            add(BoardCategoryMock.createCategory(3L, "카테고리3", 3));
        }};
        Board board = BoardMock.createHasCategoryBoard(1L, savedCategoryList);
        given(boardDomainService.getBoard(anyLong())).willReturn(board);
        willDoNothing().given(boardDomainService).duplicateBoardCheck(any(BoardType.class), anyString(), anyLong());
        willDoNothing().given(boardDomainService).possibleDeleteCategoryCheck(any(Board.class), anySet());

        long boardId = 10;
        String boardName = "수정할 게시판명";
        List<SaveAndUpdateBoardCategoryParameterDTO> updateCategoryList = List.of(
            SaveAndUpdateBoardCategoryParameterDTO.builder()
                .categoryId(1L)
                .categoryName("수정할 카테고리1")
                .orderNumber(1).build(),
            SaveAndUpdateBoardCategoryParameterDTO.builder()
                .categoryName("수정할 카테고리2")
                .orderNumber(2)
                .build(),
            SaveAndUpdateBoardCategoryParameterDTO.builder()
                .categoryName("수정할 카테고리3")
                .orderNumber(3)
                .build());
        boardManageServiceV1.updateBoard(boardId, boardName, updateCategoryList);

        assertThat(board.getName()).isEqualTo(boardName);
        assertThat(board.getCategoryList().size()).isEqualTo(updateCategoryList.size());
        IntStream.range(0, board.getCategoryList().size()).forEach(idx -> {
            BoardCategory category = board.getCategoryList().get(idx);
            SaveAndUpdateBoardCategoryParameterDTO updateCategory = updateCategoryList.get(idx);
            assertThat(category.getId()).isEqualTo(updateCategory.getCategoryId());
            assertThat(category.getName()).isEqualTo(updateCategory.getCategoryName());
            assertThat(category.getOrderNumber()).isEqualTo(updateCategory.getOrderNumber());
        });

        verify(boardDomainService).getBoard(boardId);
        verify(boardDomainService).duplicateBoardCheck(board.getType(), boardName, boardId);
        verify(boardDomainService).possibleDeleteCategoryCheck(board, Set.of(2L, 3L));
    }

    @Test
    @DisplayName("게시판 수정 - 존재하지 않는 게시판")
    void updateBoard_notExistBoard() {
        willThrow(NotExistDataException.class).given(boardDomainService).getBoard(anyLong());

        long boardId = 10;
        String boardName = "수정할 게시판명";
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardManageServiceV1.updateBoard(boardId, boardName, List.of()));

        verify(boardDomainService).getBoard(boardId);
    }

    @Test
    @DisplayName("게시판 수정 - 중복된 게시판")
    void updateBoard_duplicatedBoard() {
        Board board = BoardMock.createBoard(1L);
        given(boardDomainService.getBoard(anyLong())).willReturn(board);
        willThrow(DuplicatedDataException.class).given(boardDomainService).duplicateBoardCheck(any(BoardType.class), anyString(), anyLong());

        long boardId = 10;
        String boardName = "수정할 게시판명";
        Throwable exception = assertThrows(DuplicatedDataException.class,
            () -> boardManageServiceV1.updateBoard(boardId, boardName, List.of()));

        verify(boardDomainService).getBoard(boardId);
        verify(boardDomainService).duplicateBoardCheck(board.getType(), boardName, boardId);
    }

    @Test
    @DisplayName("게시판 수정 - 카테고리 삭제 불가")
    void updateBoard_cannotDeleteCategory() {
        List<BoardCategory> savedCategoryList = new ArrayList<>() {{
            add(BoardCategoryMock.createCategory(1L, "카테고리1", 1));
            add(BoardCategoryMock.createCategory(2L, "카테고리2", 2));
            add(BoardCategoryMock.createCategory(3L, "카테고리3", 3));
        }};
        Board board = BoardMock.createHasCategoryBoard(1L, savedCategoryList);
        given(boardDomainService.getBoard(anyLong())).willReturn(board);
        willDoNothing().given(boardDomainService).duplicateBoardCheck(any(BoardType.class), anyString(), anyLong());
        willThrow(CannotProcessedDataException.class).given(boardDomainService).possibleDeleteCategoryCheck(any(Board.class), anySet());

        long boardId = 10;
        String boardName = "수정할 게시판명";
        List<SaveAndUpdateBoardCategoryParameterDTO> updateCategoryList = List.of(
            SaveAndUpdateBoardCategoryParameterDTO.builder().categoryId(1L).categoryName("수정할 카테고리1").build(),
            SaveAndUpdateBoardCategoryParameterDTO.builder().categoryName("수정할 카테고리2").build(),
            SaveAndUpdateBoardCategoryParameterDTO.builder().categoryName("수정할 카테고리3").build());
        Throwable exception = assertThrows(CannotProcessedDataException.class,
            () -> boardManageServiceV1.updateBoard(boardId, boardName, updateCategoryList));

        verify(boardDomainService).getBoard(boardId);
        verify(boardDomainService).duplicateBoardCheck(board.getType(), boardName, boardId);
        verify(boardDomainService).possibleDeleteCategoryCheck(board, Set.of(2L, 3L));
    }

    @Test
    @DisplayName("게시판 삭제")
    void deleteBoard() {
        Board board = BoardMock.createBoard(1L);
        given(boardDomainService.getBoard(anyLong())).willReturn(board);
        willDoNothing().given(boardDomainService).possibleDeleteBoardCheck(any(Board.class));
        willDoNothing().given(boardDomainService).deleteBoard(any(Board.class));

        long boardId = 10;
        boardManageServiceV1.deleteBoard(boardId);

        verify(boardDomainService).getBoard(boardId);
        verify(boardDomainService).possibleDeleteBoardCheck(board);
        verify(boardDomainService).deleteBoard(board);
    }

    @Test
    @DisplayName("게시판 삭제 - 존재하지 않는 게시판")
    void deleteBoard_notExistBoard() {
        willThrow(NotExistDataException.class).given(boardDomainService).getBoard(anyLong());

        long boardId = 10;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardManageServiceV1.deleteBoard(boardId));

        verify(boardDomainService).getBoard(boardId);
    }

    @Test
    @DisplayName("게시판 삭제 - 게시판 삭제 불가")
    void deleteBoard_cannotDeleteBoard() {
        Board board = BoardMock.createBoard(1L);
        given(boardDomainService.getBoard(anyLong())).willReturn(board);
        willThrow(CannotProcessedDataException.class).given(boardDomainService).possibleDeleteBoardCheck(any(Board.class));

        long boardId = 10;
        Throwable exception = assertThrows(CannotProcessedDataException.class,
            () -> boardManageServiceV1.deleteBoard(boardId));

        verify(boardDomainService).getBoard(boardId);
        verify(boardDomainService).possibleDeleteBoardCheck(board);
    }

    @Test
    @DisplayName("게시판 정보 조회")
    void getBoardInfo() {
        Board board = BoardMock.createHasCategoryBoard(1L, List.of(
            BoardCategoryMock.createCategory(1L, "카테고리1", 1),
            BoardCategoryMock.createCategory(2L, "카테고리2", 2),
            BoardCategoryMock.createCategory(3L, "카테고리3", 3)
        ));
        given(boardDomainService.getBoard(anyLong())).willReturn(board);


        long boardId = 1;
        BoardInfoDTO boardInfo = boardManageServiceV1.getBoard(boardId);

        assertThat(boardInfo.getBoardId()).isEqualTo(board.getId());
        assertThat(boardInfo.getBoardName()).isEqualTo(board.getName());
        assertThat(boardInfo.getCategoryList().size()).isEqualTo(board.getCategoryList().size());
        IntStream.range(0, board.getCategoryList().size()).forEach(idx -> {
            BoardCategory boardCategory = board.getCategoryList().get(idx);
            BoardInfoDTO.BoardCategoryDTO boardCategoryDTO = boardInfo.getCategoryList().get(idx);
            assertThat(boardCategory.getId()).isEqualTo(boardCategoryDTO.getCategoryId());
            assertThat(boardCategory.getName()).isEqualTo(boardCategoryDTO.getCategoryName());
        });
        verify(boardDomainService).getBoard(boardId);
    }

    @Test
    @DisplayName("게시판 정보 조회 - 존재하지 않는 게시판")
    void getBoardInfo_notExistBoard() {
        willThrow(NotExistDataException.class).given(boardDomainService).getBoard(anyLong());

        long boardId = 1;
        Throwable exception = assertThrows(NotExistDataException.class,
            () -> boardManageServiceV1.getBoard(boardId));

        verify(boardDomainService).getBoard(boardId);
    }

}