package com.oneteam.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }
        try {
            final String token = authHeader.substring(7);
            Jwt jwt = jwtDecoder.decode((token));
            if (jwt.getExpiresAt().isAfter(Instant.now())) {
                Map<String, Object> claims = jwt.getClaims();
                Collection<? extends GrantedAuthority> authorities = Arrays.stream(jwt.getClaims().get("role").toString().split(",")).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
                UserDetails userDetails = new User(jwt.getClaims().get("userNo").toString(), "", authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.info("JWT NULL!!");
        }
        filterChain.doFilter(request,response);
    }


}
