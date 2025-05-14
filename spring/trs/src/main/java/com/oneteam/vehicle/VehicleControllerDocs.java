package com.oneteam.vehicle;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.VehicleReqDTO;
import com.oneteam.dto.VehicleSearchReqDTO;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

@Tag(name = "차량관리", description = "차량 조회 · 등록 · 수정 · 삭제 API")
@RequestMapping("/vehicle")
public interface VehicleControllerDocs {

    @Operation(
            summary     = "차량 목록 조회",
            description = "페이지 정보와 검색 조건을 이용해 차량 목록을 조회합니다.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "목록 조회 예시",
                                            value = "{\n" +
                                                    "  \"status\": true,\n" +
                                                    "  \"result\": [\n" +
                                                    "    { \"no\":101, \"regNumber\":\"34가6789\", \"type\":2, \"name\":\"샘플 트럭\", \"licence\":4 }\n" +
                                                    "  ],\n" +
                                                    "  \"message\": null\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @GetMapping
    ResDTO findByVehicle(
            @ParameterObject Pageable pageable,
            @ParameterObject VehicleSearchReqDTO vehicleSearchReqDTO
    );

    @Operation(
            summary     = "차량 목록 조회",
            description = "페이지 정보와 검색 조건을 이용해 차량 목록을 조회합니다.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "인증 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음")
            }
    )
    @PostMapping
    ResDTO findAll(
            @ParameterObject Pageable pageable,
            @RequestBody(
                    description = "검색 조건 DTO",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = VehicleSearchReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "검색 조건 예시",
                                    value = "{ \"point\":2, \"name\":\"홍길동\" }"
                            )
                    )
            )
            @Valid VehicleSearchReqDTO vehicleSearchReqDTO
    );

    @Operation(
            summary     = "차량 단건 조회",
            description = "차량 번호에 해당하는 상세 정보를 조회합니다.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "차량 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema    = @Schema(implementation = ResDTO.class),
                                    examples  = @ExampleObject(
                                            name  = "차량 없음 예시",
                                            value = "{ \"status\":false, \"result\":null, \"message\":\"존재하지 않는 차량 입니다.\" }"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/{no:[0-9]+}")
    ResDTO findByNo(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "차량 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "차량 등록",
            description = "차량 정보를 multipart/form-data로 전송하여 등록합니다.",
            requestBody = @RequestBody(
                    description = "regNumber, type, name, licence, file(첨부 이미지)",
                    required    = true,
                    content     = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema    = @Schema(implementation = VehicleReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "등록 요청 예시",
                                    value =
                                            "regNumber=12가3456\n" +
                                                    "type=1\n" +
                                                    "name=홍길동 트럭\n" +
                                                    "licence=3\n" +
                                                    "file=@profile.png"
                            )
                    )
            ),
            responses   = {
                    @ApiResponse(responseCode = "201", description = "등록 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 입력")
            }
    )
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResDTO register(
            @ModelAttribute @Valid VehicleReqDTO vehicleReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "차량 수정",
            description = "기존 차량 정보를 수정합니다.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 입력"),
                    @ApiResponse(responseCode = "404", description = "차량 없음")
            }
    )
    @PatchMapping("/{no:[0-9]+}")
    ResDTO modify(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "수정할 차량 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @RequestBody(
                    description = "수정할 차량 정보 DTO",
                    required    = true,
                    content     = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(implementation = VehicleReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "수정 요청 예시",
                                    value = "{\"status\":2, \"fileNo\":21}"
                            )
                    )
            )
            @Valid VehicleReqDTO vehicleReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "차량 삭제",
            description = "차량 정보를 삭제합니다.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "차량 없음")
            }
    )
    @DeleteMapping("/{no:[0-9]+}")
    ResDTO delete(
            @Parameter(
                    name        = "no",
                    in          = ParameterIn.PATH,
                    description = "삭제할 차량 고유번호",
                    required    = true,
                    example     = "123"
            )
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );
}
