package org.winners.backoffice.presentation.board.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.backoffice.application.board.dto.BoardInfoDTO;
import org.winners.backoffice.config.ControllerTest;
import org.winners.backoffice.config.WithMockUser;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.board.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BoardControllerTest extends ControllerTest {

    public BoardControllerTest() {
        super(BoardController.class);
    }

    @MockBean
    private BoardManageService boardManageService;

    @Test
    @DisplayName("게시판 등록")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void saveBoard() {
        willDoNothing().given(boardManageService).saveBoard(any(BoardType.class), anyString(), any(LinkedHashSet.class));

        BoardType boardType = BoardType.INQUIRY;
        String boardName = "게시판명";
        LinkedHashSet<String> categoryNames = new LinkedHashSet<>() {{ add("카테고리1"); add("카테고리2"); }};
        mvcTest(HttpMethod.POST)
            .requestBody(
                Map.entry("boardType", boardType.toString()),
                Map.entry("boardName", boardName),
                Map.entry("categoryNames", categoryNames))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(boardManageService).saveBoard(boardType, boardName, categoryNames);
    }

    @Test
    @DisplayName("게시판 수정")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void updateBoard() {
        willDoNothing().given(boardManageService).updateBoard(anyLong(), anyString(), anyList());

        long boardId = 1;
        String boardName = "게시판명";
        List<Map<String, Object>> categoryList = List.of(
            Map.ofEntries(
                Map.entry("categoryId", 1L),
                Map.entry("categoryName", "카테고리1"),
                Map.entry("orderNumber", 1)),
            Map.ofEntries(
                Map.entry("categoryId", 2L),
                Map.entry("categoryName", "카테고리2"),
                Map.entry("orderNumber", 2)),
            Map.ofEntries(
                Map.entry("categoryName", "카테고리3"),
                Map.entry("orderNumber", 3)));

        mvcTest("{boardId}", HttpMethod.PUT)
            .pathVariable(Map.entry("boardId", boardId))
            .requestBody(
                Map.entry("boardName", boardName),
                Map.entry("categoryList", categoryList))
            .responseType(ApiResponseType.SUCCESS)
            .run();
    }

    @Test
    @DisplayName("게시판 삭제")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void deleteBoard() {
        willDoNothing().given(boardManageService).deleteBoard(anyLong());

        long boardId = 1L;
        mvcTest(HttpMethod.DELETE)
            .requestBody(Map.entry("boardId", boardId))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(boardManageService).deleteBoard(boardId);
    }

    @Test
    @DisplayName("게시판 정보 조회")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void getBoard() {
        Board board = BoardMock.createHasCategoryBoard(1L, List.of(
            BoardCategoryMock.createCategory(1L, "카테고리1", 1),
            BoardCategoryMock.createCategory(2L, "카테고리2", 2),
            BoardCategoryMock.createCategory(3L, "카테고리3", 3)
        ));
        BoardInfoDTO boardInfo = BoardInfoDTO.create(board);
        given(boardManageService.getBoard(anyLong())).willReturn(boardInfo);

        long boardId = 1;
        mvcTest("{boardId}", HttpMethod.GET)
            .pathVariable(Map.entry("boardId", boardId))
            .responseBody(
                Map.entry("boardId", board.getId()),
                Map.entry("boardName", board.getName())
            )
            .responseBody(createListResponse(
                board.getCategoryList(),
                "categoryList",
                Map.entry("categoryId", BoardCategory::getId),
                Map.entry("categoryName", BoardCategory::getName)
            ))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(boardManageService).getBoard(boardId);
    }


}