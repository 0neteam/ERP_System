package com.oneteam.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ApiFilter extends AbstractGatewayFilterFactory<ApiFilter.Config> {

    public static class Config {}

    public ApiFilter() {
        super(Config.class);
    }

//  참고: https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/developer-guide.html
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpStatusCode status = response.getStatusCode();
        InetAddress address = request.getRemoteAddress().getAddress();
        URI uri = request.getURI();
        
        Map<String, List<HttpCookie>> cookies = request.getCookies();
        List<HttpCookie> tokens = cookies.get("access_token");

        if (tokens != null && !tokens.isEmpty()) {
            String accessToken = "Bearer " + tokens.get(0).getValue();
            ServerHttpRequest mutatedRequest = request.mutate().header("Authorization", accessToken).build();
            exchange = exchange.mutate().request(mutatedRequest).build();
            request = exchange.getRequest();
        }
        
        log.info("[API 필터] 요청 -> IP : {}, PORT : {}, PATH : {}", address, uri.getPort(), uri.getPath());
        request.getHeaders().forEach((key, value) -> {
    //        log.info("[요청 Header] {} : {}", key, value);
        });

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("[API 필터] 응답 -> URI : {}, 응답코드 : {}", uri, status);
            response.getHeaders().forEach((key, value) -> {
    //          log.info("[응답 Header] {} : {}", key, value);
            });
        }));

        };
    }

}
