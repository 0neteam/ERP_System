package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 기사 배정 요청 데이터")
public class OrderReqDTO {

  private Long userNo;
  private Long vehicleNo;

}
