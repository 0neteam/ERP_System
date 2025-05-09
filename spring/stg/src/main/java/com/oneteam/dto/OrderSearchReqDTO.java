package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 조회 요청 데이터")
public class OrderSearchReqDTO {

  private Integer type;
  private String orderNo;
  private Integer status;
  private String reqDateStart;
  private String reqDateEnd;
  private String perDateStart;
  private String perDateEnd;
  private String cancelDateStart;
  private String cancelDateEnd;

}
