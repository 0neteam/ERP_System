package com.oneteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "품목 요청 데이터")
public class ItemReqDTO {

  @NotBlank(message = "품목명을 입력하세요.")
  private String name;
  @NotNull(message = "묶음을 선택하세요.")
  private int bundle;
  private Integer price;
  private Long fileNo;
  private MultipartFile file;

}
