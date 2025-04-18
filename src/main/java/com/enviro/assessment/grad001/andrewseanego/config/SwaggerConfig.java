package com.enviro.assessment.grad001.andrewseanego.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI wasteManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Waste Management API")
                        .description("Spring Boot REST API for Waste Management")
                        .version("1.0.0")
                        .contact(new Contact().name("Andrew Seanego")
                                .url("https://www.linkedin.com/in/andrew-seanego-1a4013280/")
                                .email("andrewseanego@gmail.com"))
                        .license(new License().name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Waste Management API Documentation")
                        .url("https://github.com/SeanXIV/Waste-sorting-api"));
    }
}
