package com.oneteam.dept;

import com.oneteam.dto.DeptReqDTO;
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
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "부서관리", description = "관리자 권한으로 부서 조회 · 상세 · 등록 · 삭제 API")
public interface DeptControllerDocs {

    @Operation(
            summary = "부서 목록 조회",
            description = "사용 여부(useYn='Y')인 모든 부서를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResDTO.class),
                                    examples = @ExampleObject(
                                            name  = "목록 조회 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": {\n" +
                                                            "    \"list\": [\n" +
                                                            "      { \"no\": 1, \"name\": \"ADMIN\", \"deptName\": \"관리자\" },\n" +
                                                            "      { \"no\": 2, \"name\": \"MFR\", \"deptName\": \"제조\" }\n" +
                                                            "    ]\n" +
                                                            "  },\n" +
                                                            "  \"message\": null\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @GetMapping
    ResDTO findAll();

    @Operation(
            summary = "부서 상세 조회",
            description = "특정 부서 정보를 조회합니다.",
            parameters = {
                    @Parameter(name        = "no",
                            in          = ParameterIn.PATH,
                            description = "조회할 부서 번호",
                            required    = true,
                            example     = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "상세 조회 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": {\n" +
                                                            "    \"no\": 1,\n" +
                                                            "    \"name\": \"MFR\",\n" +
                                                            "    \"deptName\": \"제조\",\n" +
                                                            "    \"regUserName\": \"admin\",\n" +
                                                            "    \"modUserName\": null,\n" +
                                                            "    \"regDate\": \"2025-04-01 10:00\",\n" +
                                                            "    \"modDate\": null\n" +
                                                            "  },\n" +
                                                            "  \"message\": null\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "부서 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name  = "부서 없음 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 부서 입니다.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @PostMapping("/{no:[0-9]+}")
    ResDTO findByNo(
            @PathVariable("no") Long no,
            Authentication authentication
    );

    @Operation(
            summary     = "부서 등록",
            description = "새로운 부서를 등록합니다.",
            requestBody = @RequestBody(
                    description = "등록할 부서 정보 (name, deptName)",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = DeptReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "등록 요청 예시",
                                    value = "{\"name\":\"인사부\",\"deptName\":\"HR팀\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "등록 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "등록 성공 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": { \"no\": 3, \"name\": \"인사부\", \"deptName\": \"HR팀\" },\n" +
                                                            "  \"message\": null\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @PutMapping
    ResDTO register(
            @Valid @RequestBody DeptReqDTO deptReqDTO,
            Authentication authentication
    );

    @Operation(
            summary = "부서 삭제",
            description = "부서를 useYn='N' 으로 변경하여 삭제 처리합니다.",
            parameters = {
                    @Parameter(name        = "no",
                            in          = ParameterIn.PATH,
                            description = "삭제할 부서 번호",
                            required    = true,
                            example     = "2")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "삭제 성공 예시",
                                            value = "{\"status\":true,\"result\":null,\"message\":null}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "부서 없음",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name  = "부서 없음 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 부서 입니다.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @DeleteMapping("/{no:[0-9]+}")
    ResDTO delete(
            @PathVariable("no") Long no,
            Authentication authentication
    );
}
