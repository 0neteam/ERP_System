package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "출고 요청 데이터")
public class ReleaseItemReqDTO {

  private Integer point;
  private Long orderNo;
  private List<ReleaseReqDTO> releases;

}
