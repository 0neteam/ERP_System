package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 요청 데이터")
public class OrderReqDTO {

//  @Schema(description = "발주 승인일", defaultValue = "2025-05-19")
//  private LocalDateTime perDate;
//  @Schema(description = "발주 취소일", defaultValue = "2025-05-19")
//  private LocalDateTime cancelDate;

  @Schema(description = "발주 상태", defaultValue = "1")
  private Integer status;

  @Schema(description = "발주 품목 내역", defaultValue = "[]")
  private List<OrderItemDTO> items;

}
