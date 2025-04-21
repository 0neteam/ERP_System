package com.oneteam.config;

import java.util.List;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPI3Configuration {


  @Value("${swagger.api-gateway-url}")
  private String apiGatewayUrl;

  @Value("${spring.application.name}")
  private String name;

  private final String ACCESS = "Authorization_Access";

  @Bean
  public OpenAPI openApi() {
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(ACCESS);
    SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
//        .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(ACCESS);
    Components components = new Components().addSecuritySchemes(ACCESS, accessTokenSecurityScheme);

    return new OpenAPI()
            .info(new Info()
                    .title(name + " Service APIs")
                    .description("Gateway " + name + " Service APIs")
                    .version("v1.0.0")
                    .contact(new Contact().name("0neteam").url(apiGatewayUrl)))
            .servers(List.of(new Server().url(apiGatewayUrl)))
            .addSecurityItem(securityRequirement)
            .components(components);
  }
}
