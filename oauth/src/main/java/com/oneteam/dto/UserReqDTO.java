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
public class UserReqDTO {

    @NotBlank(message = "이메일를 입력하세요.")
    private String email;

    //  @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String name;
    @NotBlank(message = "유형을 선택하세요.")
    private String type;

}
