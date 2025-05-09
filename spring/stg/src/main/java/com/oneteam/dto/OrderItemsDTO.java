package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 상세 품목 응답 데이터")
public class OrderItemsDTO {

  private Long no;
  private Long itemNo;
  private int bundle;
  private int qty;
  private BigDecimal oQty;
  private BigDecimal iQty;
  private BigDecimal pQty;

}
