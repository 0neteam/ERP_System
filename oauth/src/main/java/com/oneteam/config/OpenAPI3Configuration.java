package com.oneteam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPI3Configuration {

  @Value("${swagger.api-gateway-url}")
  private String apiGatewayUrl;
  
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("OAUTH")
            .description("인증인가 서버")
            .version("v1.0.1")
            .contact(new Contact().name("Github: ERP_System").url("https://github.com/0neteam/ERP_System")))
        .servers(List.of(new Server().url(apiGatewayUrl)));
  }

}
