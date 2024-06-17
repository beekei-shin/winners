package org.winners.app.presentation.board.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.app.application.board.AppBoardPostService;
import org.winners.app.application.board.dto.BoardPostInfoDTO;
import org.winners.app.application.board.dto.BoardPostListDTO;
import org.winners.app.application.board.dto.GetPostListSearchParameterDTO;
import org.winners.app.presentation.AppController;
import org.winners.app.presentation.board.v1.request.SavePostRequestDTO;
import org.winners.app.presentation.board.v1.request.UpdatePostRequestDTO;
import org.winners.app.presentation.board.v1.response.GetPostInfoResponseDTO;
import org.winners.app.presentation.board.v1.response.GetPostListResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.presentation.PagingRequestDTO;
import org.winners.core.config.version.ApiVersion;
import org.winners.core.utils.SecurityUtil;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.INQUIRY_BOARD_TAG_NAME)
@RequestMapping(path = AppController.INQUIRY_BOARD_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class InquiryBoardController {

    private final AppBoardPostService inquiryBoardPostServiceV1;

    @Operation(summary = "문의글 목록 조회")
    @GetMapping(name = "문의글 목록 조회", value = "post")
    public ApiResponse<GetPostListResponseDTO> getPostList(@RequestParam(required = false) String keyword,
                                                           @ParameterObject @ModelAttribute PagingRequestDTO paging) {
        Page<BoardPostListDTO> postList = inquiryBoardPostServiceV1.getPostList(
            GetPostListSearchParameterDTO.builder()
                .keyword(keyword)
                .build(),
            paging.of());
        return ApiResponse.success(GetPostListResponseDTO.convert(postList));
    }

    @Operation(summary = "문의글 정보 조회")
    @GetMapping(name = "문의글 정보 조회", value = "post/{postId}")
    public ApiResponse<GetPostInfoResponseDTO> getPostInfo(@PathVariable @Min(value = 1) long postId) {
        long userId = SecurityUtil.getTokenUserId();
        BoardPostInfoDTO postInfo = inquiryBoardPostServiceV1.getPostInfo(postId, userId);
        return ApiResponse.success(GetPostInfoResponseDTO.convert(postInfo));
    }

    @Operation(summary = "문의글 등록")
    @PostMapping(name = "문의글 등록", value = "post")
    public ApiResponse<?> savePost(@RequestBody @Valid SavePostRequestDTO request) {
        long userId = SecurityUtil.getTokenUserId();
        inquiryBoardPostServiceV1.savePost(userId, request.convertParameter());
        return ApiResponse.success();
    }

    @Operation(summary = "문의글 수정")
    @PutMapping(name = "문의글 수정", value = "post/{postId}")
    public ApiResponse<?> updatePost(@PathVariable @Min(value = 1) long postId, @RequestBody @Valid UpdatePostRequestDTO request) {
        long userId = SecurityUtil.getTokenUserId();
        inquiryBoardPostServiceV1.updatePost(userId, postId, request.convertParameter());
        return ApiResponse.success();
    }

}
