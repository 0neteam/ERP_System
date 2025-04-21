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

  //yml설정을 이 config 클래스에 바인딩 하기위해 클래스 지정
    public ApiFilter() {
        super(Config.class);
    }

//  참고: https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/developer-guide.html
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
        // 현재 요청과 응답 객체를 가져옴
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpStatusCode status = response.getStatusCode();
        // 클라이언트의 IP 주소와 요청 URI 정보 추출
        InetAddress address = request.getRemoteAddress().getAddress();
        URI uri = request.getURI();
        
        // 요청 쿠키에서 'access_token' 추출
        Map<String, List<HttpCookie>> cookies = request.getCookies();
        List<HttpCookie> tokens = cookies.get("access_token");

        // 'access_token' 쿠키가 존재하면 Authorization 헤더에 추가
        if (tokens != null && !tokens.isEmpty()) {
            String accessToken = "Bearer " + tokens.get(0).getValue();
            // 요청에 Authorization 헤더를 추가한 새로운 요청 객체 생성
            ServerHttpRequest mutatedRequest = request.mutate().header("Authorization", accessToken).build();
            // 새로운 요청 객체로 교체
            exchange = exchange.mutate().request(mutatedRequest).build();
            request = exchange.getRequest();
        }
        
        // 요청의 IP, 포트, URI 정보를 로그에 출력
        log.info("[API 필터] 요청 -> IP : {}, PORT : {}, PATH : {}", address, uri.getPort(), uri.getPath());
        request.getHeaders().forEach((key, value) -> {
    //        log.info("[요청 Header] {} : {}", key, value);
        });

        // 필터 체인으로 요청을 넘기고, 응답 후 작업을 추가
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 응답이 끝난 후 URI와 응답 코드 정보를 로그로 출력
            log.info("[API 필터] 응답 -> URI : {}, 응답코드 : {}", uri, status);
            response.getHeaders().forEach((key, value) -> {
    //          log.info("[응답 Header] {} : {}", key, value);
            });
        }));

        };
    }

}
