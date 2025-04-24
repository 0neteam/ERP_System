package com.oneteam.home;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;

@Tag(name="테스트관리", description = "")
public interface HomeControllerDocs {

  @Operation(summary = "ROOT 경로 확인", description = "서비스 확인용")
  public String home();

  @Operation(summary = "로그인 확인", description = "로그인 시 사용자 권한 확인용")
  public Object me(Authentication authentication);

}
