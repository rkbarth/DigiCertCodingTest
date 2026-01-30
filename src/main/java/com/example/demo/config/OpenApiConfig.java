package com.example.demo.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // use a relative server URL so Swagger UI issues requests relative to the UI path (works with proxy/preview hosts)
        Server relativeServer = new Server().url("../").description("Relative server URL (one level up from UI path)");
        return new OpenAPI()
                .info(new Info().title("DigiCert Demo API").version("v1").description("API for Certificate demo"))
                .servers(List.of(relativeServer));
    }
}
