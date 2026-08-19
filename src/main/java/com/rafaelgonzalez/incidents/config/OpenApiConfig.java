package com.rafaelgonzalez.incidents.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI incidentManagementOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Incident Management API")
                .version("1.0.0")
                .description("API for registering, assigning, filtering and resolving operational incidents."));
    }
}
