package com.winners.appApi.presentation.user.v1;

import com.winners.appApi.presentation.user.v1.BusinessUserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import com.winners.appApi.application.user.BusinessUserInfoService;
import com.winners.appApi.config.ControllerTest;
import com.winners.appApi.config.WithMockUser;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenRole;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BusinessUserControllerTest extends ControllerTest {

    protected BusinessUserControllerTest() {
        super(BusinessUserController.class);
    }

    @MockBean
    private BusinessUserInfoService businessUserInfoService;

    @Test
    @DisplayName("비밀번호 변경 여부 조회")
    @WithMockUser(userId = 2L, authorities = TokenRole.BUSINESS_USER)
    void isUpdatedPassword() {
        boolean isUpdatedPassword = true;
        given(businessUserInfoService.isUpdatedPassword(anyLong())).willReturn(isUpdatedPassword);

        mvcTest("updated-password", HttpMethod.GET)
            .requestBody(Map.entry("isUpdatedPassword", isUpdatedPassword))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(businessUserInfoService).isUpdatedPassword(2L);
    }

    @Test
    @DisplayName("비밀번호 변경")
    @WithMockUser(userId = 2L, authorities = TokenRole.BUSINESS_USER)
    void updatePassword() {
        willDoNothing().given(businessUserInfoService).updatePassword(anyLong(), anyString());

        String password = "newPassword486";
        mvcTest("password", HttpMethod.PUT)
            .requestBody(Map.entry("password", password))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(businessUserInfoService).updatePassword(2L, password);
    }

}