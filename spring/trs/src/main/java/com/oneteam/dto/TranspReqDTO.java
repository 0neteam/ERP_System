package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "운송 요청 데이터")
public class TranspReqDTO {

    private Integer point;
    private Long userNo;
    private Long vehicleNo;

}
