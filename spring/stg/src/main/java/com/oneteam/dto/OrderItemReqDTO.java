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
@Schema(description = "발주 품목 요청 데이터")
public class OrderItemReqDTO {

  @NotBlank(message = "발주번호를 입력하세요.")
  private Long orderNo;
  private List<OrderItemDTO> items;
  
}
