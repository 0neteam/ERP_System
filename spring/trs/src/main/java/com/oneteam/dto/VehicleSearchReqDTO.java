package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "차량 조회 요청 데이터")
public class VehicleSearchReqDTO {

  private Integer point;
  private String orderNo;
  private String regNumber;
  private String name;
  private Integer type;
  private Integer licence;
  private Integer status;

}
