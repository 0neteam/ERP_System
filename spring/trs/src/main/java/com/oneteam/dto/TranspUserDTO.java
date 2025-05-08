package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "운송 기사 응답 데이터")
public class TranspUserDTO {

  private Long no;
  private String name;
  private String email;
  private Long fileNo;

}
