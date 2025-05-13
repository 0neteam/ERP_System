package com.oneteam.oauth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;

import java.util.Map;

@Tag(name = "OAuth관리", description = "OAuth 관련 API 문서를 제공합니다.")
public interface OAuthClientControllerDocs {

//    홈 엔드포인트
    @Operation(
            summary = "홈 엔드포인트",
            description = "인증 정보가 있으면 인증 권한을 로그에 기록하고 'AUTHORIZATION' 문자열을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "홈 엔드포인트 호출 성공",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "AUTHORIZATION")
                    )
            )
    })
    String home(Authentication authentication);

//    JWK Set 정보 조회
    @Operation(
            summary = "JWK Set 정보 조회",
            description = "서버의 공개 키(JWKSet)를 JSON 객체 형태로 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공개 키 정보를 성공적으로 반환",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    example = "{\n" +
                                            "  \"keys\": [\n" +
                                            "    {\n" +
                                            "      \"kty\": \"RSA\",\n" +
                                            "      \"use\": \"sig\",\n" +
                                            "      \"kid\": \"abc123\",\n" +
                                            "      \"alg\": \"RS256\",\n" +
                                            "      \"n\": \"0vx7agoebGcQSuuPiLJXZptNvn1iwZddw9apq\",\n" +
                                            "      \"e\": \"AQAB\"\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                            )
                    )
            )
    })
    Map<String, Object> keys();
}
