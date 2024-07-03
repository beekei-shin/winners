package org.winners.app.presentation.user.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.winners.app.application.user.BusinessUserInfoService;
import org.winners.app.presentation.AppController;
import org.winners.app.presentation.user.v1.request.IsUpdatedPasswordResponseDTO;
import org.winners.app.presentation.user.v1.request.UpdatePasswordRequestDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.version.ApiVersion;
import org.winners.core.utils.SecurityUtil;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.BUSINESS_USER_TAG_NAME)
@RequestMapping(path = AppController.BUSINESS_USER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessUserController {

    private final BusinessUserInfoService businessUserInfoService;

    @Operation(summary = "비밀번호 변경 여부 조회")
    @GetMapping(name = "비밀번호 변경 여부 조회", path = "updated-password")
    public ApiResponse<IsUpdatedPasswordResponseDTO> isUpdatedPassword() {
        long userId = SecurityUtil.getTokenUserId();
        boolean isUpdatedPassword = businessUserInfoService.isUpdatedPassword(userId);
        return ApiResponse.success(new IsUpdatedPasswordResponseDTO(isUpdatedPassword));
    }

    @Operation(summary = "비밀번호 변경")
    @PutMapping(name = "비밀번호 변경", path = "password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> updatePassword(@RequestBody @Valid UpdatePasswordRequestDTO request) {
        long userId = SecurityUtil.getTokenUserId();
        businessUserInfoService.updatePassword(userId, request.getPassword());
        return ApiResponse.success();
    }

}
