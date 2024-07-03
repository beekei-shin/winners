package com.winners.backofficeApi.presentation.user.v1;

import com.winners.backofficeApi.application.user.BusinessUserManageService;
import com.winners.backofficeApi.config.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import com.winners.backofficeApi.config.WithMockUser;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.security.token.TokenRole;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

class BusinessUserManageControllerTest extends ControllerTest {

    protected BusinessUserManageControllerTest() {
        super(BusinessUserManageController.class);
    }

    @MockBean
    private BusinessUserManageService businessUserManageService;

    @Test
    @DisplayName("사업자 회원 등록")
    @WithMockUser(authorities = TokenRole.ADMIN_USER)
    void saveBusinessUser() {
        willDoNothing().given(businessUserManageService).saveBusinessUser(anyString(), anyString(), anyString());

        String userName = "홍길동";
        String phoneNumber = "01012341234";
        String password = "password123";
        mvcTest(HttpMethod.POST)
            .requestBody(
                Map.entry("userName", userName),
                Map.entry("phoneNumber", phoneNumber),
                Map.entry("password", password))
            .responseType(ApiResponseType.SUCCESS)
            .run();

        verify(businessUserManageService).saveBusinessUser(userName, phoneNumber, password);
    }

}