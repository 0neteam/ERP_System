package com.oneteam.user;

import com.oneteam.dto.AuthReqDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserInfoReqDTO;
import com.oneteam.dto.UserReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

@Tag(name = "사용자관리", description = "로그인 · 회원가입 · 사용자 정보 조회/수정 · 이메일 인증 · 로그아웃 API")
@RequestMapping("/user")
public interface UserControllerDocs {

    @Operation(
            summary = "사용자 정보 조회",
            description = "현재 인증된 사용자의 프로필 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "조회 성공 예시",
                                            value = "{\"status\":true,\"result\":{...},\"message\":null}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "인증 실패 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"Unauthorized\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "접근 권한 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "접근 권한 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"Access Denied\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "사용자 정보 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "사용자 없음 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 사용자 입니다.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 오류",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "서버 오류 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"Internal Server Error\"}"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    ResDTO userInfo(Authentication authentication);


    @Operation(
            summary = "로그인",
            description = "이메일 인증코드를 받아 로그인 처리 후, JWT 토큰을 쿠키에 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "로그인 성공 예시",
                                            value = "{\"status\":true,\"result\":\"ROLE_USER\",\"message\":\"홍길동님 환영합니다.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "잘못된 요청 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"인증 번호를 입력하세요.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "인증 실패 또는 사용자 없음",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "인증 실패 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 사용자 입니다.\"}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    ResDTO signIn(
            @RequestBody(
                    description = "로그인용 인증코드",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthReqDTO.class),
                            examples = @ExampleObject(
                                    name = "로그인 요청 예시",
                                    value = "{\"code\":\"A1B2C3\"}"
                            )
                    )
            )
            @Valid AuthReqDTO authReqDTO,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    );


    @Operation(
            summary = "회원가입",
            description = "이메일 · 이름 · 타입(type=\"1\") 으로 회원가입을 완료합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가입 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "가입 성공 예시",
                                            value = "{\"status\":true,\"result\":null,\"message\":\"정상적으로 가입이 되었습니다.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "잘못된 요청 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"이메일를 입력하세요.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일 또는 잘못된 데이터",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "중복 이메일 예시",
                                            value = "{\"status\":false,\"message\":\"이미 사용 중인 이메일 입니다.\"}"
                                    )
                            )
                    )
            }
    )
    @PutMapping
    ResDTO signUp(
            @RequestBody(
                    description = "회원가입 정보 (email, name, type)",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserReqDTO.class),
                            examples = @ExampleObject(
                                    name = "회원가입 요청 예시",
                                    value = "{\n" +
                                            "  \"email\": \"newuser@example.com\",\n" +
                                            "  \"name\": \"새사용자\",\n" +
                                            "  \"type\": \"1\"\n" +
                                            "}"
                            )
                    )
            )
            @Valid UserReqDTO userDto
    );


    @Operation(
            summary = "회원 정보 수정",
            description = "면허 정보(licence1~4)와 프로필 이미지(file)을 multipart/form-data로 전송하여 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "수정 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "수정 성공 예시",
                                            value = "{\"status\":true,\"message\":null}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "인증 실패 예시",
                                            value = "{\"status\":false,\"message\":\"Unauthorized\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "사용자 없음",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "사용자 없음 예시",
                                            value = "{\"status\":false,\"message\":\"존재하지 않는 사용자 입니다.\"}"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{no:[0-9]+}")
    ResDTO modify(
            @Schema(description = "수정할 사용자 번호", example = "1")
            @PathVariable Long no,

            @RequestBody(
                    description = "multipart/form-data: licence1~4 (Y/N), file",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = UserInfoReqDTO.class),
                            examples = @ExampleObject(
                                    name = "회원 정보 수정 예시",
                                    value = "licence1=Y\nlicence2=Y\nlicence3=N\nlicence4=N\nfile=@profile.png"
                            )
                    )
            )
            @ModelAttribute @Valid UserInfoReqDTO userInfoReqDTO,

            Authentication authentication
    );


    @Operation(
            summary = "이메일 인증번호 요청",
            description = "회원가입(type=1) 또는 로그인(type=2)용 이메일 인증번호를 발송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "발송 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "발송 성공 예시",
                                            value = "{\"status\":true,\"message\":\"\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 이메일 형식",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "잘못된 요청 예시",
                                            value = "{\"status\":false,\"message\":\"유효하지 않은 이메일 형식입니다.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "중복 이메일 예시",
                                            value = "{\"status\":false,\"message\":\"이미 사용 중인 이메일 입니다.\"}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/email")
    ResDTO email(
            @RequestBody(
                    description = "인증번호 요청 정보 (email, type)",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserReqDTO.class),
                            examples = @ExampleObject(
                                    name = "이메일 요청 예시",
                                    value = "{\"email\":\"hong@example.com\",\"type\":\"2\"}"
                            )
                    )
            )
            @Valid UserReqDTO userDto
    );


    @Operation(
            summary = "이메일 인증 확인",
            description = "이메일로 받은 인증번호(code)를 입력하여 인증 완료 여부를 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "인증 성공 예시",
                                            value = "{\"status\":true,\"message\":\"인증코드 확인 완료\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 인증번호",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "인증 실패 예시",
                                            value = "{\"status\":false,\"message\":\"유효하지 않는 인증번호 입니다.\"}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/auth")
    ResDTO auth(
            @RequestBody(
                    description = "인증 확인 요청 (code)",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthReqDTO.class),
                            examples = @ExampleObject(
                                    name = "인증 요청 예시",
                                    value = "{\"code\":\"A1B2C3\"}"
                            )
                    )
            )
            @Valid AuthReqDTO authReqDTO
    );


    @Operation(
            summary = "로그아웃",
            description = "세션과 쿠키의 인증 정보를 삭제하여 로그아웃 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                            content = @Content(
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name = "로그아웃 예시",
                                            value = "{\"status\":true}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/logout")
    ResDTO logout(HttpServletRequest request, HttpServletResponse response);

}
