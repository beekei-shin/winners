package org.winners.backoffice.presentation.board.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.backoffice.application.board.BoardPostManageService;
import org.winners.backoffice.application.board.dto.BoardPostInfoDTO;
import org.winners.backoffice.application.board.dto.BoardPostListDTO;
import org.winners.backoffice.application.board.dto.GetPostListSearchParameterDTO;
import org.winners.backoffice.presentation.BackofficeController;
import org.winners.backoffice.presentation.board.v1.response.GetPostInfoResponseDTO;
import org.winners.backoffice.presentation.board.v1.response.GetPostListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.PagingRequestDTO;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = BackofficeController.BOARD_POST_TAG_NAME)
@RequestMapping(path = BackofficeController.BOARD_POST_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardPostController {

    private final BoardPostManageService boardPostManageServiceV1;

    @Operation(summary = "게시글 목록 조회")
    @GetMapping(name = "게시글 목록 조회", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<GetPostListResponseDTO> getPostList(@RequestParam(required = false) @Min(value = 1) Long boardId,
                                                           @RequestParam(required = false) String keyword,
                                                           @ParameterObject @ModelAttribute PagingRequestDTO paging) {
        Page<BoardPostListDTO> postList = boardPostManageServiceV1.getPostList(
            GetPostListSearchParameterDTO.builder().
                boardId(boardId).
                keyword(keyword)
                .build(),
            paging.of());
        return ApiResponse.success(GetPostListResponseDTO.convert(postList));
    }

    @Operation(summary = "게시글 정보 조회")
    @GetMapping(name = "게시글 정보 조회", value = "{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<GetPostInfoResponseDTO> getPostInfo(@PathVariable @Min(value = 1) long postId) {
        BoardPostInfoDTO postInfo = boardPostManageServiceV1.getPostInfo(postId);
        return ApiResponse.success(GetPostInfoResponseDTO.convert(postInfo));
    }

}
