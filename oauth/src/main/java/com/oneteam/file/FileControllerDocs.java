package com.oneteam.file;

import com.oneteam.dto.ResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일관리", description = "파일 업로드 · 다운로드 API")
public interface FileControllerDocs {

    @Operation(
            summary     = "파일 업로드",
            description = "multipart/form‑data로 실제 파일을 업로드합니다.",
            requestBody = @RequestBody(
                    description = "업로드할 파일 (form field: file)",
                    required    = true,
                    content     = @Content(
                            mediaType = "multipart/form-data"
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "업로드 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "업로드 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": { \"no\": 1, \"url\": \"http://localhost:9000/file/i/1\" },\n" +
                                                            "  \"message\": \"정상적으로 파일 저장이 되었습니다.\"\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 필요")
            }
    )
    @PostMapping
    ResDTO upload(
            @Parameter(description = "업로드할 파일", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "파일 다운로드/조회",
            description = "저장된 파일을 스트림으로 반환합니다.",
            parameters = {
                    @Parameter(name        = "type",
                            in          = ParameterIn.PATH,
                            description = "파일 종류 (i=이미지, u=사용자용)",
                            required    = true,
                            example     = "i"),
                    @Parameter(name        = "no",
                            in          = ParameterIn.PATH,
                            description = "파일 번호",
                            required    = true,
                            example     = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "파일 조회 성공",
                            content = @Content(mediaType = "application/octet-stream")
                    ),
                    @ApiResponse(responseCode = "404", description = "파일을 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증 필요")
            }
    )
    @GetMapping("/{type:[iu]}/{no:[0-9]+}")
    ResponseEntity<?> uri(
            @PathVariable("type") String type,
            @PathVariable("no")   Long no,
            @Parameter(hidden = true) Authentication authentication
    );
}
