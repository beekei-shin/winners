package com.winners.appApi.presentation.user.v1;

import com.winners.appApi.presentation.user.v1.BusinessUserSignController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import com.winners.appApi.application.user.BusinessUserSignService;
import com.winners.appApi.application.user.dto.SignInBusinessUserResultDTO;
import com.winners.appApi.config.ControllerTest;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.domain.auth.service.dto.AuthTokenDTO;
import org.winners.core.domain.user.BusinessUser;
import org.winners.core.domain.user.BusinessUserMock;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class BusinessUserSignControllerTest extends ControllerTest {

    protected BusinessUserSignControllerTest() {
        super(BusinessUserSignController.class);
    }

    @MockBean
    private BusinessUserSignService businessUserSignService;

    @Test
    @DisplayName("사업자 회원 로그인")
    void signInBusinessUser() {
        BusinessUser businessUser = BusinessUserMock.createUser(1L);
        AuthTokenDTO authToken = AuthTokenDTO.create("accessToken", "refreshToken");
        boolean isUpdatedPassword = true;
        given(businessUserSignService.signIn(anyString(), anyString()))
            .willReturn(SignInBusinessUserResultDTO.success(businessUser, isUpdatedPassword, authToken));

        String phoneNumber = "01012341234";
        String password = "password486";
        mvcTest("in", HttpMethod.POST)
            .requestBody(
                Map.entry("phoneNumber", phoneNumber),
                Map.entry("password", password))
            .responseBody(
                Map.entry("userId", businessUser.getId()),
                Map.entry("userName", businessUser.getName()),
                Map.entry("phoneNumber", businessUser.getPhoneNumber()),
                Map.entry("isUpdatedPassword", isUpdatedPassword),
                Map.entry("accessToken", authToken.getAccessToken()),
                Map.entry("refreshToken", authToken.getRefreshToken()))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(businessUserSignService).signIn(phoneNumber, password);
    }

}