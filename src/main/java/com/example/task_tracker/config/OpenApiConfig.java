package com.example.task_tracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info=@Info(
                contact=@Contact(
                        name= "elaa",
                        email= "elaa.brahmii@gmail.com",
                        url="https://elaBrahmi.com"
                ),
                description = "OpenApi documentation for spring security",
                title="OpenApi specification",
                version="1.0",
                license=@License(
                        name="license name",
                        url="https://some-url.com"
                ),
                termsOfService = "terms of service"
        ),
        servers={
                @Server(
                        description = "local env",
                        url="http://localhost:8088/api/v1" //this is the base url ,the rest will be handled by swagger
                ),
                @Server(
                        description = "prod env",
                        url="http://other-url"

                )

        },
        security={
                @SecurityRequirement( // for every controller or api we need a security requirement
                        name="bearerAuth"
                )
        }
)
@SecurityScheme( //must have the same name as security requirement
        name = "bearerAuth",
        description = "JWT auth description",
        scheme ="bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in =SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
