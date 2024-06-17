package org.winners.backoffice.presentation.board.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.backoffice.application.board.dto.BoardInfoDTO;
import org.winners.backoffice.presentation.BackofficeController;
import org.winners.backoffice.presentation.board.v1.request.DeleteBoardRequestDTO;
import org.winners.backoffice.presentation.board.v1.request.SaveBoardRequestDTO;
import org.winners.backoffice.presentation.board.v1.request.UpdateBoardRequestDTO;
import org.winners.backoffice.presentation.board.v1.response.GetBoardInfoResponseDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = BackofficeController.BOARD_TAG_NAME)
@RequestMapping(path = BackofficeController.BOARD_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final BoardManageService boardManageServiceV1;

    @Operation(summary = "게시판 등록")
    @PostMapping(name = "게시판 등록")
    public ApiResponse<?> saveBoard(@RequestBody @Valid SaveBoardRequestDTO request) {
        boardManageServiceV1.saveBoard(request.getBoardType(), request.getBoardName(), request.getCategoryNames());
        return ApiResponse.success();
    }

    @Operation(summary = "게시판 수정")
    @PutMapping(name = "게시판 수정", value = "{boardId}")
    public ApiResponse<?> updateBoard(@PathVariable @Min(value = 1) long boardId, @RequestBody @Valid UpdateBoardRequestDTO request) {
        boardManageServiceV1.updateBoard(boardId, request.getBoardName(), request.convertParameter());
        return ApiResponse.success();
    }

    @Operation(summary = "게시판 삭제")
    @DeleteMapping(name = "게시판 삭제")
    public ApiResponse<?> deleteBoard(@RequestBody @Valid DeleteBoardRequestDTO request) {
        boardManageServiceV1.deleteBoard(request.getBoardId());
        return ApiResponse.success();
    }

    @Operation(summary = "게시판 정보 조회")
    @GetMapping(name = "게시판 정보 조회", value = "{boardId}")
    public ApiResponse<GetBoardInfoResponseDTO> getBoardInfo(@PathVariable @Min(value = 1) long boardId) {
        BoardInfoDTO boardInfo = boardManageServiceV1.getBoard(boardId);
        return ApiResponse.success(GetBoardInfoResponseDTO.convert(boardInfo));
    }

}
