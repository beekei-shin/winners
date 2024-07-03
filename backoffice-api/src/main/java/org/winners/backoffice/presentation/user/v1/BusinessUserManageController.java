package org.winners.backoffice.presentation.user.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.backoffice.application.user.BusinessUserManageService;
import org.winners.backoffice.presentation.BackofficeController;
import org.winners.backoffice.presentation.user.v1.request.SaveBusinessUserRequestDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = BackofficeController.BUSINESS_USER_MANAGE_TAG_NAME)
@RequestMapping(path = BackofficeController.BUSINESS_USER_MANAGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessUserManageController {

    private final BusinessUserManageService businessUserManageServiceV1;

    @Operation(summary = "사업자 회원 등록")
    @PostMapping(name = "사업자 회원 등록", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> saveBusinessUser(@RequestBody @Valid SaveBusinessUserRequestDTO request) {
        businessUserManageServiceV1.saveBusinessUser(request.getUserName(), request.getPhoneNumber(), request.getPassword());
        return ApiResponse.success();
    }

}
