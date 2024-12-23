package com.bank.transfer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerTransferConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().servers(List.of(new Server()
                        .url("http://localhost:8092")))
                .info(new Info().title("Transfers API"));
    }
}
