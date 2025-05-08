package com.oneteam.user;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserMngReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "직원관리", description = "관리자 권한으로 직원 조회·수정·삭제 API")
public interface UserMngControllerDocs {

    @Operation(
            summary     = "직원 목록 조회",
            description = "이름 필터, 페이지, 사이즈, 정렬 옵션으로 직원 목록을 조회합니다.",
            parameters  = {
                    @Parameter(
                            name        = "name",
                            in          = ParameterIn.QUERY,
                            description = "이름 검색(부분일치)",
                            required    = false,
                            example     = "홍"
                    ),
                    @Parameter(
                            name        = "page",
                            in          = ParameterIn.QUERY,
                            description = "페이지 번호 (0부터 시작)",
                            required    = false,
                            example     = "0"
                    ),
                    @Parameter(
                            name        = "size",
                            in          = ParameterIn.QUERY,
                            description = "페이지 크기",
                            required    = false,
                            example     = "10"
                    ),
                    @Parameter(
                            name        = "sort",
                            in          = ParameterIn.QUERY,
                            description = "정렬 기준 (필드,asc/desc)",
                            required    = false,
                            example     = "name,asc"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description  = "조회 성공",
                            content      = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "목록 조회 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": {\n" +
                                                            "    \"list\": [ /* ... */ ],\n" +
                                                            "    \"totalElements\": 1,\n" +
                                                            "    \"totalPages\": 1,\n" +
                                                            "    \"size\": 10\n" +
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
    ResDTO findAll(
            @RequestParam(name = "name", required = false) String name,
            // Swagger UI 에서는 숨기고 실제 매핑에는 사용될 파라미터
            @io.swagger.v3.oas.annotations.Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable
    );

    @Operation(
            summary = "직원 상세 조회",
            description = "특정 직원의 상세 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "no", description = "조회할 직원 번호", required = true, example = "2")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "상세 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "상세 조회 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": {\n" +
                                                            "    \"no\": 2,\n" +
                                                            "    \"email\": \"lee@example.com\",\n" +
                                                            "    \"name\": \"이순신\",\n" +
                                                            "    \"fileNo\": 5,\n" +
                                                            "    \"licence1\": \"Y\",\n" +
                                                            "    \"licence2\": \"Y\",\n" +
                                                            "    \"licence3\": \"N\",\n" +
                                                            "    \"licence4\": \"N\",\n" +
                                                            "    \"useYn\": \"Y\",\n" +
                                                            "    \"depts\": [],\n" +
                                                            "    \"regDate\": \"2025-03-20 09:30\",\n" +
                                                            "    \"regUserName\": \"admin\",\n" +
                                                            "    \"modDate\": \"2025-03-21 10:00\",\n" +
                                                            "    \"modUserName\": \"admin\"\n" +
                                                            "  },\n" +
                                                            "  \"message\": null\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "직원 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name  = "직원 없음 예시",
                                            value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 직원 입니다.\"}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/{no:[0-9]+}")
    ResDTO findByNo(
            @PathVariable("no") Long no,
            Authentication authentication
    );

    @Operation(
            summary = "직원 정보 수정",
            description = "직원의 면허 정보를 수정합니다.",
            parameters = {
                    @Parameter(name = "no", description = "수정할 직원 번호", required = true, example = "2")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 면허 정보",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = UserMngReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "수정 요청 예시",
                                    value = "{\"licence1\":\"Y\",\"licence2\":\"N\",\"licence3\":\"Y\",\"licence4\":\"N\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "수정 성공 예시",
                                            value = "{\"status\":true,\"result\":null,\"message\":null}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "직원 없음")
            }
    )
    @PatchMapping("/{no:[0-9]+}")
    ResDTO modify(
            @PathVariable("no") Long no,
            @Valid UserMngReqDTO userMngReqDTO,
            Authentication authentication
    );

    @Operation(
            summary = "직원 삭제",
            description = "직원의 useYn을 'N'으로 변경하여 삭제 처리합니다.",
            parameters = {
                    @Parameter(name = "no", description = "삭제할 직원 번호", required = true, example = "2")
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
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "직원 없음")
            }
    )
    @DeleteMapping("/{no:[0-9]+}")
    ResDTO delete(
            @PathVariable("no") Long no,
            Authentication authentication
    );
}
