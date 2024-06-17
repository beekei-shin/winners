package org.winners.app.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.winners.core.config.presentation.ApiResponseType;
import org.winners.core.config.version.ApiVersion;
import org.winners.core.config.version.ApiVersionRequestMappingConfig;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
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

    protected String controllerPath;

    @SneakyThrows
    protected ControllerTest(Class<?> controllerClass) {
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            Annotation mappingAnnotation = controllerClass.getAnnotation(RequestMapping.class);
            Annotation versionAnnotation = controllerClass.getAnnotation(ApiVersion.class);
            String[] path = (String[]) RequestMapping.class.getMethod("path").invoke(mappingAnnotation);
            String version = versionAnnotation == null ? "" : String.valueOf(ApiVersion.class.getMethod("value").invoke(versionAnnotation));
            this.controllerPath = ApiVersionRequestMappingConfig.VERSION_PREFIX + version + "/" + path[0];
        }
    }

    @BeforeAll
    protected void init() {
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    protected MvcTestBase mvcTest(HttpMethod httpMethod) {
        return new MvcTestBase(this, "", httpMethod);
    }

    protected MvcTestBase mvcTest(String apiPath, HttpMethod httpMethod) {
        return new MvcTestBase(this, apiPath, httpMethod);
    }

    @SneakyThrows
    protected void run(MvcTestBase mvcTestBase) {
        HttpMethod httpMethod = mvcTestBase.getHttpMethod();
        String apiPath = mvcTestBase.getApiPath();
        String pullPath = "/" + this.controllerPath + (StringUtils.isNotBlank(apiPath) ? "/" + apiPath : "");

        Map<String, Object> pathVariable = mvcTestBase.getPathVariable();
        if (pathVariable != null && !pathVariable.isEmpty()) {
            for (String key : pathVariable.keySet()) {
                pullPath = pullPath.replace("{" + key + "}", String.valueOf(pathVariable.get(key)));
            }
        }

        MockHttpServletRequestBuilder servletRequest =
            request(httpMethod, pullPath)
                .contentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> requestParams = mvcTestBase.getRequestParams();
        if (requestParams != null && !requestParams.isEmpty())
            servletRequest.params(requestParams);

        Map<String, Object> requestBody = mvcTestBase.getRequestBody();
        if (requestBody != null && !requestBody.isEmpty())
            servletRequest.content(objectMapper.writeValueAsString(requestBody));

        ApiResponseType responseType = mvcTestBase.getResponseType();
        ResultActions resultActions = mockMvc.perform(servletRequest)
            .andExpect(MockMvcResultMatchers.status().is(responseType.getHttpStatus().value()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(responseType.getCode()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage").value(responseType.getMessage()));

        Map<String, Object> responseBody = mvcTestBase.getResponseBody();
        if (responseBody != null && !responseBody.isEmpty()) {
            resultActions.andExpectAll(responseBody.keySet().stream()
                .map(key -> MockMvcResultMatchers.jsonPath("$.responseData." + key).value(responseBody.get(key)))
                .toArray(ResultMatcher[]::new));
        }

        resultActions.andDo(print());
    }

    protected <R> Map<String, Object> createPageResponse(Page<R> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", page.getNumber() + 1);
        response.put("currentSize", page.getSize());
        response.put("totalPage", page.getTotalPages());
        response.put("totalCount", page.getTotalElements());
        return response;
    }

    protected <R> Map<String, Object> createListResponse(List<R> list, String listFieldName, Map.Entry<String, Function<R, Object>> ... entries) {
        Map<String, Object> response = new HashMap<>();
        IntStream.range(0, list.size()).forEach(idx -> {
            R row = list.get(idx);
            for (Map.Entry<String, Function<R, Object>> entry : entries) {
                String testFieldName = listFieldName + "[" + idx + "]." + entry.getKey();
                Object testFieldValue = entry.getValue().apply(row);
                response.put(testFieldName, testFieldValue);
            }
        });
        return response;
    }

}


