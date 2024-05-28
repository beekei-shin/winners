package org.winners.app.presentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.winners.app.config.MockMvcConfig;
import org.winners.core.config.presentation.ApiResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockMvcConfig
@SpringBootTest
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Setter(AccessLevel.PROTECTED)
    private String version;

    @Setter(AccessLevel.PROTECTED)
    private String controllerPath;

    @SneakyThrows
    protected void postTest(String apiPath, Object request, Object response) {
        mockMvc.perform(post("/" + this.version + "/" + this.controllerPath + "/" + apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

    @SneakyThrows
    protected void putTest(String apiPath, Object request, Object response) {
        mockMvc.perform(put("/" + this.version + "/" + this.controllerPath + "/" + apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

}

