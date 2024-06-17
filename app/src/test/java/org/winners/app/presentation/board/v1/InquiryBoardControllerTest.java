package org.winners.app.presentation.board.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.winners.app.application.board.AppBoardPostService;
import org.winners.app.application.board.dto.BoardPostInfoDTO;
import org.winners.app.application.board.dto.BoardPostListDTO;
import org.winners.app.application.board.dto.GetPostListSearchParameterDTO;
import org.winners.app.application.board.dto.UpdatePostParameterDTO;
import org.winners.app.config.ControllerTest;
import org.winners.app.config.WithMockUser;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenRole;
import org.winners.core.domain.board.service.dto.SavePostParameterDTO;
import org.winners.core.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class InquiryBoardControllerTest extends ControllerTest {

    public InquiryBoardControllerTest() {
        super(InquiryBoardController.class);
    }

    @MockBean
    private AppBoardPostService inquiryBoardPostServiceV1;

    @Test
    @DisplayName("문의글 목록 조회")
    @WithMockUser(authorities = TokenRole.CLIENT_USER)
    void getPostList() {
        List<BoardPostListDTO> postList = List.of(
            new BoardPostListDTO(2L, 1L, "작성자명", 1L, "게시판명", 1L, "카테고리1", "제목2",  LocalDateTime.now(), null),
            new BoardPostListDTO(1L, 1L, "작성자명", 1L, "게시판명", 1L, "카테고리1", "제목1",  LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1).plusHours(3))
        );
        Page<BoardPostListDTO> postPage = new PageImpl<>(postList, PageRequest.of(0, 10), postList.size());
        given(inquiryBoardPostServiceV1.getPostList(any(GetPostListSearchParameterDTO.class), any(PageRequest.class)))
            .willReturn(postPage);

        mvcTest("post", HttpMethod.GET)
            .responseBody(createPageResponse(postPage))
            .responseBody(createListResponse(
                postPage.getContent(),
                "postList",
                Map.entry("postId", BoardPostListDTO::getPostId),
                Map.entry("userId", BoardPostListDTO::getUserId),
                Map.entry("userName", BoardPostListDTO::getUserName),
                Map.entry("boardId", BoardPostListDTO::getBoardId),
                Map.entry("boardName", BoardPostListDTO::getBoardName),
                Map.entry("categoryId", BoardPostListDTO::getCategoryId),
                Map.entry("categoryName", BoardPostListDTO::getCategoryName),
                Map.entry("postTitle", BoardPostListDTO::getPostTitle),
                Map.entry("postCreatedDatetime", dto -> DateUtil.formatDatetime(dto.getPostCreatedDatetime())),
                Map.entry("postUpdatedDatetime", dto -> Optional.ofNullable(dto.getPostUpdatedDatetime()).map(DateUtil::formatDatetime).orElse(null))))
            .responseType(ApiResponseType.SUCCESS)
            .run();
    }

    @Test
    @DisplayName("문의글 정보 조회")
    @WithMockUser(userId = 2, authorities = TokenRole.CLIENT_USER)
    void getPostInfo() {
        BoardPostInfoDTO postInfo = new BoardPostInfoDTO(2L, 1L, "작성자명", 1L, "게시판명", 1L, "카테고리명", "제목", "내용", true, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        given(inquiryBoardPostServiceV1.getPostInfo(anyLong(), anyLong())).willReturn(postInfo);

        long postId = 1;
        mvcTest("post/{postId}", HttpMethod.GET)
            .pathVariable(Map.entry("postId", postId))
            .responseBody(
                Map.entry("postId", postInfo.getPostId()),
                Map.entry("userId", postInfo.getUserId()),
                Map.entry("userName", postInfo.getUserName()),
                Map.entry("boardId", postInfo.getBoardId()),
                Map.entry("boardName", postInfo.getBoardName()),
                Map.entry("categoryId", postInfo.getCategoryId()),
                Map.entry("categoryName", postInfo.getCategoryName()),
                Map.entry("postTitle", postInfo.getPostTitle()),
                Map.entry("postContents", postInfo.getPostContents()),
                Map.entry("isSecretPost", postInfo.isSecretPost()),
                Map.entry("postCreatedDatetime", DateUtil.formatDatetime(postInfo.getPostCreatedDatetime())),
                Map.entry("postUpdatedDatetime", DateUtil.formatDatetime(postInfo.getPostUpdatedDatetime())))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(inquiryBoardPostServiceV1).getPostInfo(postId, 2);
    }

    @Test
    @DisplayName("문의글 등록")
    @WithMockUser(userId = 2, authorities = TokenRole.CLIENT_USER)
    void savePost() {
        willDoNothing().given(inquiryBoardPostServiceV1).savePost(anyLong(), any(SavePostParameterDTO.class));

        mvcTest("post", HttpMethod.POST)
            .requestBody(
                Map.entry("categoryId", 1L),
                Map.entry("postTitle", "문의글 제목"),
                Map.entry("postContents", "문의글 내용"),
                Map.entry("isSecretPost", true))
            .responseType(ApiResponseType.SUCCESS)
            .run();
    }

    @Test
    @DisplayName("문의글 수정")
    @WithMockUser(userId = 2, authorities = TokenRole.CLIENT_USER)
    void updatePost() {
        willDoNothing().given(inquiryBoardPostServiceV1).updatePost(anyLong(), anyLong(), any(UpdatePostParameterDTO.class));

        long postId = 1;
        mvcTest("post/{postId}", HttpMethod.PUT)
            .pathVariable(Map.entry("postId", postId))
            .requestBody(
                Map.entry("categoryId", 1L),
                Map.entry("postTitle", "문의글 제목"),
                Map.entry("postContents", "문의글 내용"),
                Map.entry("isSecretPost", true))
            .responseType(ApiResponseType.SUCCESS)
            .run();
    }

}