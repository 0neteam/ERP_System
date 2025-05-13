package com.oneteam.oauth;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthClientController implements OAuthClientControllerDocs {

  private final JWKSet jwkSet;

  @GetMapping("/")
  public String home(Authentication authentication) {
    if(authentication != null) log.info("Auth : {}", authentication.getAuthorities());
    return "AUTHORIZATION";
  }

  @GetMapping("/.well-known/jwks.json")
  public Map<String, Object> keys() {
    return jwkSet.toJSONObject();
  }

}
