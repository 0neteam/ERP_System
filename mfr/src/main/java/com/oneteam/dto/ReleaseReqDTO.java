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
@Schema(description = "입출고 요청 데이터")
public class ReleaseReqDTO {

  @NotBlank(message = "발주번호를 입력하세요.")
  private Long orderItemNo;
  @NotBlank(message = "품목번호를 입력하세요.")
  private Long itemNo;
  private Long transpNo;
  @NotBlank(message = "수량을 입력하세요.")
  private int qty;

}
