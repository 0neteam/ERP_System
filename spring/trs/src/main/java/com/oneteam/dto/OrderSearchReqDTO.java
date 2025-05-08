package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 기사 조회 요청 데이터")
public class OrderSearchReqDTO {

  private Integer type;
  private String name;

}
