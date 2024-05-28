package org.winners.app.presentation;


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
import org.winners.app.config.MockMvcConfig;
import org.winners.core.config.presentation.ApiResponse;

import java.lang.annotation.Annotation;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @SneakyThrows
    protected void postTest(String apiPath, Object request, Object response) {
        mockMvc.perform(post("/" + controllerPath + "/" + apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

    @SneakyThrows
    protected void putTest(String apiPath, Object request, Object response) {
        mockMvc.perform(put("/" + controllerPath + "/" + apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ApiResponse.success(response))))
            .andDo(print());
    }

}


