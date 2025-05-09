package com.oneteam.dept;

import com.oneteam.dto.DeptUserReqDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "부서직원관리", description = "관리자 권한으로 부서 직원 조회·등록·삭제 API")
public interface DeptUserControllerDocs {

    @Operation(
            summary     = "부서 직원 조회",
            description = "특정 부서(no)에 속한 직원들을 페이지 단위로 조회합니다.",
            parameters  = {
                    @Parameter(name        = "no",
                            in          = ParameterIn.PATH,
                            description = "부서 번호",
                            required    = true,
                            example     = "1"),
                    @Parameter(name        = "name",
                            in          = ParameterIn.QUERY,
                            description = "이름 검색(부분일치)",
                            required    = false,
                            example     = "홍길동"),
                    @Parameter(name        = "page",
                            in          = ParameterIn.QUERY,
                            description = "페이지 번호 (0부터 시작)",
                            example     = "0"),
                    @Parameter(name        = "size",
                            in          = ParameterIn.QUERY,
                            description = "페이지 크기",
                            example     = "12"),
                    @Parameter(name        = "sort",
                            in          = ParameterIn.QUERY,
                            description = "정렬 기준 (필드,asc/desc)",
                            example     = "no,asc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "조회 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": {\n" +
                                                            "    \"list\": [ /* UserDTO 배열 */ ],\n" +
                                                            "    \"totalElements\": 10,\n" +
                                                            "    \"totalPages\": 1,\n" +
                                                            "    \"size\": 12\n" +
                                                            "  },\n" +
                                                            "  \"message\": null\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @GetMapping("/{no:[0-9]+}")
    ResDTO findAll(
            @PathVariable("no") Long no,
            @RequestParam(name = "name", required = false) String name,
            @Parameter(hidden = true)
            @PageableDefault(size = 12, sort = "no", direction = Sort.Direction.ASC)
            Pageable pageable
    );

    @Operation(
            summary     = "부서 미할당 직원 조회",
            description = "특정 부서(no)에 아직 할당되지 않은 직원들을 페이지 단위로 조회합니다.",
            parameters  = {
                    @Parameter(name        = "no",
                            in          = ParameterIn.PATH,
                            description = "부서 번호",
                            required    = true,
                            example     = "1"),
                    @Parameter(name        = "page",
                            in          = ParameterIn.QUERY,
                            description = "페이지 번호",
                            example     = "0"),
                    @Parameter(name        = "size",
                            in          = ParameterIn.QUERY,
                            description = "페이지 크기",
                            example     = "12"),
                    @Parameter(name        = "sort",
                            in          = ParameterIn.QUERY,
                            description = "정렬 기준",
                            example     = "no,asc")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "미할당 조회 예시",
                                            value =
                                                    "{\n" +
                                                            "  \"status\": true,\n" +
                                                            "  \"result\": {\n" +
                                                            "    \"list\": [ /* UserDTO 배열 */ ],\n" +
                                                            "    \"totalElements\": 5,\n" +
                                                            "    \"totalPages\": 1,\n" +
                                                            "    \"size\": 12\n" +
                                                            "  },\n" +
                                                            "  \"message\": null\n" +
                                                            "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @PostMapping("/{no:[0-9]+}")
    ResDTO findByNo(
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            Pageable pageable
    );

    @Operation(
            summary     = "부서 직원 등록",
            description = "DeptUserReqDTO.users 리스트에 담긴 직원들을 부서(no)에 등록합니다.",
            requestBody = @RequestBody(
                    description = "등록할 직원 번호 배열",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = DeptUserReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "등록 요청 예시",
                                    value = "{\"users\":[3,5,7]}"
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
                                            value = "{\"status\":true,\"result\":null,\"message\":null}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @PutMapping("/{no:[0-9]+}")
    ResDTO register(
            @PathVariable("no") Long no,
            @Valid @RequestBody DeptUserReqDTO deptUserReqDTO,
            Authentication authentication
    );

    @Operation(
            summary     = "부서 직원 삭제",
            description = "DeptUserReqDTO.users 리스트에 담긴 직원들을 부서(no)에서 삭제(useYn='N') 처리합니다.",
            requestBody = @RequestBody(
                    description = "삭제할 직원 번호 배열",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = DeptUserReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "삭제 요청 예시",
                                    value = "{\"users\":[3,5,7]}"
                            )
                    )
            ),
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
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @PatchMapping("/{no:[0-9]+}")
    ResDTO delete(
            @PathVariable("no") Long no,
            @Valid @RequestBody DeptUserReqDTO deptUserReqDTO,
            Authentication authentication
    );
}
