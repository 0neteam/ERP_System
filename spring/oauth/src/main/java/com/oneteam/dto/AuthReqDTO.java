package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "임시 인증 번호 확인")
public class AuthReqDTO {

  @NotBlank(message = "인증 번호를 입력하세요.")
  private String code;

}
