package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "차량 요청 데이터")
public class VehicleReqDTO {

  private int point;
  private String regNumber;
  private int type;
  private String name;
  private int licence;
  private int status;
  private Long fileNo;
  private MultipartFile file;

}
