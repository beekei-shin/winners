package org.winners.backoffice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        final String securitySchemeName = "JWT Authentication";
        return new OpenAPI()
            .info(apiInfo())
            .servers(List.of(
                new Server().url("/app").description("APP"),
                new Server().url("/bo").description("BACKOFFICE")))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components().addSecuritySchemes(
                securitySchemeName,
                new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT")));
    }

    private Info apiInfo() {
        return new Info()
            .title("Winners API Document")
            .description("Winners API 문서")
            .license(new License().name("beekei").url(""))
            .version("1.0.0");
    }

}
