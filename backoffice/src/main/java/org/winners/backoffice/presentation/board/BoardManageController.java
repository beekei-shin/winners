package org.winners.backoffice.presentation.board;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.winners.backoffice.presentation.board.request.DeleteBoardRequestDTO;
import org.winners.backoffice.presentation.board.request.SaveBoardRequestDTO;
import org.winners.backoffice.presentation.board.request.UpdateBoardRequestDTO;
import org.winners.core.config.presentation.ApiResponse;

public interface BoardManageController {

    @Operation(summary = "게시판 등록")
    @PostMapping(name = "게시판 등록", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> saveBoard(@RequestBody @Valid SaveBoardRequestDTO request);

    @Operation(summary = "게시판 수정")
    @PutMapping(name = "게시판 수정", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> updateBoard(@RequestBody @Valid UpdateBoardRequestDTO request);

    @Operation(summary = "게시판 삭제")
    @DeleteMapping(name = "게시판 삭제", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> deleteBoard(@RequestBody @Valid DeleteBoardRequestDTO request);

}
