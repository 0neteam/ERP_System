package com.oneteam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockReqDTO {

  @NotNull(message = "품목을 선택하세요.")
  private Long itemNo;
  @NotNull(message = "수량을 선택하세요.")
  private int qty;

}
