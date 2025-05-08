package com.oneteam.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController implements HomeControllerDocs {

  @GetMapping("/")
  public String home() {
    return "STG";
  }

//  @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_STG') or hasRole('ROLE_MFR')")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/me")
  public Object me(Authentication authentication) {
    log.info("Auth : {}", authentication.getAuthorities());
    return authentication.getPrincipal();
  }

}
