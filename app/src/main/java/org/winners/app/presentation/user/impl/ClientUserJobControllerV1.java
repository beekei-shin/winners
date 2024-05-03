package org.winners.app.presentation.user.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.app.application.user.ClientUserJobService;
import org.winners.app.presentation.user.ClientUserJobController;
import org.winners.app.presentation.user.request.SaveClientUserJobRequestDTO;
import org.winners.core.config.presentation.ApiResponse;
import org.winners.core.config.security.SecurityUtils;

@Tag(name = "202.v1. 사용자 회원 직업")
@RestController
@RequestMapping("v1/user/client/job")
@RequiredArgsConstructor
public class ClientUserJobControllerV1 implements ClientUserJobController {

    private final ClientUserJobService clientUserJobServiceV1;

    @Override
    public ApiResponse<?> saveClientUserJob(SaveClientUserJobRequestDTO request) {
        long userId = SecurityUtils.getTokenUserId();
        clientUserJobServiceV1.saveClientUserJob(userId, request.getJobIds());
        return ApiResponse.success();
    }

}
