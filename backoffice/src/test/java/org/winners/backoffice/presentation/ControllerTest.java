package org.winners.backoffice.presentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.winners.backoffice.MockMvcConfig;
import org.winners.core.config.presentation.ApiResponse;

import java.lang.annotation.Annotation;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@MockMvcConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private String controllerPath;

    @SneakyThrows
    public ControllerTest(Class<?> controllerClass) {
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            Annotation annotation = controllerClass.getAnnotation(RequestMapping.class);
            Optional.ofNullable(RequestMapping.class.getMethod("path").invoke(annotation))
                .map(path -> (String[]) path)
                .map(path -> path[0])
                .ifPresent(path -> this.controllerPath = path);
        }
    }

    @BeforeAll
    public void init() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    protected void postTest(Object request, Object response) {
        postTest(null, request, response);
    }

    @SneakyThrows
    protected void postTest(String apiPath, Object request, Object response) {
        mockMvc.perform(post("/" + controllerPath + Optional.ofNullable(apiPath).map(s -> "/" + s).orElse(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

    protected void putTest(Object request, Object response) {
        putTest(null, request, response);
    }

    @SneakyThrows
    protected void putTest(String apiPath, Object request, Object response) {
        mockMvc.perform(put("/" + controllerPath + Optional.ofNullable(apiPath).map(s -> "/" + s).orElse(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

    protected void deleteTest(Object request, Object response) {
        deleteTest(null, request, response);
    }

    @SneakyThrows
    protected void deleteTest(String apiPath, Object request, Object response) {
        mockMvc.perform(delete("/" + controllerPath + Optional.ofNullable(apiPath).map(s -> "/" + s).orElse(""))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

}


