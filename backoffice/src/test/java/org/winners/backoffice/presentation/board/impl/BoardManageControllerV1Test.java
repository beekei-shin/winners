package org.winners.backoffice.presentation.board.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.backoffice.presentation.ControllerTest;
import org.winners.backoffice.presentation.board.request.DeleteBoardRequestDTO;
import org.winners.backoffice.presentation.board.request.SaveBoardRequestDTO;
import org.winners.backoffice.presentation.board.request.UpdateBoardRequestDTO;
import org.winners.core.domain.board.BoardType;

import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BoardManageControllerV1Test extends ControllerTest {

    public BoardManageControllerV1Test() {
        super(BoardManageControllerV1.class);
    }

    @MockBean
    private BoardManageService boardManageService;

    @Test
    @DisplayName("게시판 등록")
    void saveBoard() {
        willDoNothing().given(boardManageService).saveBoard(any(BoardType.class), anyString(), any(LinkedHashSet.class));

        SaveBoardRequestDTO request = new SaveBoardRequestDTO(BoardType.COMMUNITY.toString(), "커뮤니티",
            new LinkedHashSet() {{
                add("카테고리1");
                add("카테고리2");
            }});
        postTest(request, null);

        verify(boardManageService).saveBoard(request.getBoardType(), request.getBoardName(), request.getCategoryNames());
    }

    @Test
    @DisplayName("게시판 수정")
    void updateBoard() {
        willDoNothing().given(boardManageService).updateBoard(anyLong(), anyString(), anyList());

        UpdateBoardRequestDTO request = new UpdateBoardRequestDTO(1L, "커뮤니티",
            List.of(
                new UpdateBoardRequestDTO.UpdateCategoryRequestDTO(1L, "카테고리1", 1),
                new UpdateBoardRequestDTO.UpdateCategoryRequestDTO(2L, "카테고리2", 2),
                new UpdateBoardRequestDTO.UpdateCategoryRequestDTO(null, "카테고리3", 3)
            ));

        putTest(request, null);
    }


    @Test
    @DisplayName("게시판 삭제")
    void deleteBoard() {
        willDoNothing().given(boardManageService).deleteBoard(anyLong());

        DeleteBoardRequestDTO request = new DeleteBoardRequestDTO(1L);
        deleteTest(request, null);

        verify(boardManageService).deleteBoard(request.getBoardId());
    }

}