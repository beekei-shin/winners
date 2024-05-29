package org.winners.backoffice.presentation.board.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.backoffice.application.board.BoardManageService;
import org.winners.backoffice.presentation.board.BackofficeController;
import org.winners.backoffice.presentation.board.BoardManageController;
import org.winners.backoffice.presentation.board.request.DeleteBoardRequestDTO;
import org.winners.backoffice.presentation.board.request.SaveBoardRequestDTO;
import org.winners.core.config.presentation.ApiResponse;

@RestController
@Tag(name = "v1." + BackofficeController.BOARD_MANAGE_TAG_NAME)
@RequestMapping(path = "v1/" + BackofficeController.BOARD_MANAGE_PATH)
@RequiredArgsConstructor
public class BoardManageControllerV1 implements BoardManageController {

    private final BoardManageService boardManageServiceV1;

    @Override
    public ApiResponse<?> saveBoard(SaveBoardRequestDTO request) {
        boardManageServiceV1.saveBoard(request.getBoardType(), request.getBoardName(), request.getCategoryNames());
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<?> deleteBoard(DeleteBoardRequestDTO request) {
        boardManageServiceV1.deleteBoard(request.getBoardId());
        return ApiResponse.success();
    }

}
