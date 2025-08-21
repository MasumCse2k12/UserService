package com.river.demo.config;


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
    public OpenAPI applicationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Application API")
                        .description("Spring Boot 3.5.4 REST API Documentation")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Masum Billah")
                                .email("masum.billah@example.com")
                                .url("https://www.linkedin.com/in/md-masum-billah-78656b126/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://github.com/myorg/myrepo"));
    }
}
