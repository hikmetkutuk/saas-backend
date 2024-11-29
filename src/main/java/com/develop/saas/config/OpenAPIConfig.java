package com.develop.saas.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@OpenAPIDefinition(
        info =
                @Info(
                        title = "SaaS API",
                        version = "1.0.0",
                        description = "Comprehensive API Documentation",
                        contact =
                                @Contact(
                                        name = "Hikmet Kütük",
                                        email = "hikmetkutuk@engineer.com",
                                        url = "https://github.com/hikmetkutuk")),
        security = {@SecurityRequirement(name = "bearerToken")})
@Import(SpringDocConfiguration.class)
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8080").description("Local Development Server")));
    }
}
