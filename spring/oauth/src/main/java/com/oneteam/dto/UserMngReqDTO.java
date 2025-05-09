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
@Schema(description = "사용자 요청 데이터")
public class UserMngReqDTO {

    @NotBlank(message = "면허1를 입력하세요.")
    private String licence1;
    @NotBlank(message = "면허2를 입력하세요.")
    private String licence2;
    @NotBlank(message = "면허3를 입력하세요.")
    private String licence3;
    @NotBlank(message = "면허4를 입력하세요.")
    private String licence4;

}
