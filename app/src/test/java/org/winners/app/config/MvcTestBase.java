package org.winners.app.config;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.winners.core.config.presentation.ApiResponseType;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MvcTestBase {

    private final ControllerTest controllerTest;
    private final String apiPath;
    private final HttpMethod httpMethod;
    private final Map<String, Object> pathVariable = new HashMap<>();
    private final MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    private final Map<String, Object> requestBody = new HashMap<>();
    private final Map<String, Object> responseBody = new HashMap<>();
    private ApiResponseType responseType = ApiResponseType.SUCCESS;

    public MvcTestBase(ControllerTest controllerTest, String apiPath, HttpMethod httpMethod) {
        this.controllerTest = controllerTest;
        this.apiPath = apiPath;
        this.httpMethod = httpMethod;
    }

    public MvcTestBase pathVariable(Map.Entry<String, Object> ... values) {
        for (Map.Entry<String, Object> value : values) {
            this.pathVariable.put(value.getKey(), String.valueOf(value.getValue()));
        }
        return this;
    }

    public MvcTestBase requestParam(Map.Entry<String, Object> ... params) {
        for (Map.Entry<String, Object> param : params) {
            this.requestParams.add(param.getKey(), String.valueOf(param.getValue()));
        }
        return this;
    }

    public MvcTestBase requestBody(Map.Entry<String, Object> ... bodys) {
        for (Map.Entry<String, Object> body : bodys) {
            this.requestBody.put(body.getKey(), body.getValue());
        }
        return this;
    }

    public MvcTestBase requestBody(Map<String, Object> body) {
        this.requestBody.putAll(body);
        return this;
    }

    public MvcTestBase responseBody(Map.Entry<String, Object> ... bodys) {
        for (Map.Entry<String, Object> body : bodys) {
            this.responseBody.put(body.getKey(), body.getValue());
        }
        return this;
    }

    public MvcTestBase responseBody(Map<String, Object> body) {
        this.responseBody.putAll(body);
        return this;
    }

    public MvcTestBase responseType(ApiResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public void run() {
        this.controllerTest.run(this);
    }

}
