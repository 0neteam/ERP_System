package com.oneteam.transp;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.TranspReqDTO;
import com.oneteam.dto.TranspSearchReqDTO;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;

@Tag(name = "운송관리", description = "운송 조회 · 등록 · 수정 · 삭제 API")
@RequestMapping("/transp")
public interface TranspControllerDocs {

    @Operation(
            summary     = "운송 목록 조회",
            description = "페이지 정보로 운송 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = ResDTO.class)
                    )
            )
    })
    @GetMapping
    ResDTO findAll(
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary     = "운송 목록 조회",
            description = "검색 조건과 페이지 정보로 운송 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = ResDTO.class)
                    )
            )
    })
    @PostMapping
    ResDTO findAll(
            @ParameterObject Pageable pageable,
            @RequestBody(
                    description = "검색 조건 DTO",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = TranspSearchReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "검색 조건 예시",
                                    value = "{ \"type\":1, \"startDate\":\"2025-04-01\", \"EndDate\":\"2025-04-30\" }"
                            )
                    )
            )
            @Valid TranspSearchReqDTO transpSearchReqDTO
    );

    @Operation(
            summary     = "운송 단건 조회",
            description = "운송 고유번호에 해당하는 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = ResDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "운송 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = ResDTO.class),
                            examples  = @ExampleObject(
                                    name  = "운송 없음 예시",
                                    value = "{ \"status\":false, \"result\":null, \"message\":\"존재하지 않는 운송 입니다.\" }"
                            )
                    )
            )
    })
    @PostMapping("/{no:[0-9]+}")
    ResDTO findByNo(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "운송 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication,
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary     = "운송 등록",
            description = "운송 정보를 등록합니다.",
            requestBody = @RequestBody(
                    description = "운송 요청 DTO",
                    required    = true,
                    content     = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema    = @Schema(implementation = TranspReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "등록 요청 예시",
                                    value = "{ \"point\":1, \"userNo\":456, \"vehicleNo\":789 }"
                            )
                    )
            )
    )
    @PutMapping("/{no:[0-9]+}")
    ResDTO register(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "운송 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @Valid @RequestBody TranspReqDTO transpReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "운송 수정",
            description = "운송 정보를 수정합니다.",
            requestBody = @RequestBody(
                    description = "변경할 운송 정보 DTO",
                    required    = true,
                    content     = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema    = @Schema(implementation = TranspReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "수정 요청 예시",
                                    value = "{ \"point\":2, \"userNo\":456, \"vehicleNo\":789 }"
                            )
                    )
            )
    )
    @PatchMapping("/{no:[0-9]+}")
    ResDTO modify(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "수정할 운송 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @Valid @RequestBody TranspReqDTO transpReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "운송 삭제",
            description = "운송 정보를 삭제합니다.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "운송 없음")
            }
    )
    @DeleteMapping("/{no:[0-9]+}")
    ResDTO delete(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "삭제할 운송 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );
}
