package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 진행 상태 응답 데이터")
public class OrderCntDTO {

  private Long orderNo;
  private BigDecimal cnt;

}
