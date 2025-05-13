package com.oneteam.config;

import com.oneteam.oauth.OAuthClientService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthorizationConfig {

    @Value("${logout.redirect-url}")
    private String redirectUrl;

    private final OAuthClientService oAuthClientService;
    private final RsaKeyProperties rsaKeys;

//CORS를 커스터마이징하여 관리하도록 설정
    @Bean
    @Order(1)
    SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher());
        http.with(authorizationServerConfigurer, Customizer.withDefaults());
//        http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests(r -> r.anyRequest().authenticated());
        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        //  GET방식 요청을 처리해 쿠키를 삭제 후 지정된 URL로 리다이렉트하여 로그아웃
        http.logout(logout -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
//            logout.logoutSuccessUrl("/");
            logout.logoutSuccessHandler((req, res, auth) -> {
                Cookie[] cookies = req.getCookies();
                if(cookies != null) {
                    for(int i = 0; i < cookies.length; i++) {
                        cookies[i].setMaxAge(0);
                        cookies[i].setPath("/");
                        res.addCookie(cookies[i]);
                    }
                }
                res.sendRedirect(redirectUrl);
            });
            logout.permitAll();
        });
        http.authorizeHttpRequests(r -> {
            r.requestMatchers(HttpMethod.GET,"/","/.well-known/jwks.json").permitAll();
            r.requestMatchers("/user/**", "/file/**").permitAll();
            r.requestMatchers("/docs","/v3/**","/swagger-ui/**").permitAll();
            r.anyRequest().authenticated();
        });
//        JWT 토큰을 기반으로 인증하는 커스텀 필터를 설정
        http.addFilterBefore(new JwtAuthenticationFilter(rsaKeys), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    CORS 커스터마이징이 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
//        설정된 redirectUrl로 CORS를 동적으로 관리
        List<String> originUris = List.of(redirectUrl);
//        특정 출처(origin)에 대한 요청을 허용
        originUris.forEach(config::addAllowedOrigin);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
//        HTTP 메서드 허용 설정
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        자격 증명 설정
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    패스워드 인코더
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    클라이언트 등록 정보를 조회하는 커스텀 구현체
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
//        DB에서 클라이언트 ID 또는 ID로 조회하여 인증처리
        return new RegisteredClientRepository() {
            @Override
            public void save(RegisteredClient registeredClient) {}
            @Override
            public RegisteredClient findById(String id) {
                return oAuthClientService.findById(id);
            }
            @Override
            public RegisteredClient findByClientId(String clientId) {
                return oAuthClientService.findByClientId(clientId);
            }
        };
    }

//    JWT 서명 검증을 위한 공개키를 외부에  제공
    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder(rsaKeys.publicKey())
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID("public-key-id");
        return new JWKSet(builder.build());
    }

//    JWT를 만들고 서명하는 데 사용
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

//    JWT 액세스 토큰을 커스터마이징
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                RegisteredClient client = context.getRegisteredClient();
                JwtClaimsSet.Builder builder = context.getClaims();
                builder.issuer("Oauth2_Server");
                builder.expiresAt(Instant.now().plus(1, ChronoUnit.DAYS));
                builder.claims((claims) -> {
                    claims.put("scope", client.getScopes());
                });
                builder.claim("username", client.getClientName());
                builder.claim("userNo", client.getId());
            }
        });
    }

}
