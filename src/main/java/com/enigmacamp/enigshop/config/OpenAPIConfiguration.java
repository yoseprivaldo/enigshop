package com.enigmacamp.enigshop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Enigshop API",
                version = "1.0.0",
                contact = @Contact(
                        name = "EnigmaCamp",
                        url = "http://enigmacamp.com"
                )
        )
)
public class OpenAPIConfiguration {
}
