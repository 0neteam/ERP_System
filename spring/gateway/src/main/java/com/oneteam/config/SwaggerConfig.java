package com.oneteam.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

  private final RouteDefinitionLocator locator;
  private final SwaggerUiConfigProperties swaggerUiConfigProperties;

  @PostConstruct
  public void init() {
    List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
    Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
    definitions.stream()
        .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
        .forEach(routeDefinition -> {
          String name = routeDefinition.getId().replaceAll("-service", "");
          String apiDocsUrl = DEFAULT_API_DOCS_URL + "/" + name;
          AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl
              = new AbstractSwaggerUiConfigProperties.SwaggerUrl(name, apiDocsUrl, null);
          urls.add(swaggerUrl);
        });
    swaggerUiConfigProperties.setUrls(urls);
  }

}
