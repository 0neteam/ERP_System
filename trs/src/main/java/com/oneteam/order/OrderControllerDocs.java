package com.oneteam.order;

import com.oneteam.dto.OrderReqDTO;
import com.oneteam.dto.OrderSearchReqDTO;
import com.oneteam.dto.ResDTO;
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

@Tag(name = "발주관리", description = "발주 기사 조회 및 배정 API")
@RequestMapping("/order")
public interface OrderControllerDocs {

    @Operation(
            summary = "발주 기사 목록 조회",
            description = "페이지 정보와 검색 조건으로 발주 가능한 운송 기사 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    ResDTO findAll(
            @ParameterObject Pageable pageable,
            @RequestBody(
                    description = "검색 조건 DTO",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderSearchReqDTO.class),
                            examples = @ExampleObject(
                                    name = "검색 조건 예시",
                                    value = "{ \"type\":1, \"name\":\"홍길동\" }"
                            )
                    )
            )
            @Valid OrderSearchReqDTO orderSearchReqDTO
    );

    @Operation(
            summary = "발주 기사 배정",
            description = "발주 번호에 운송 기사를 배정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "배정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "404", description = "리소스 없음")
    })
    @PutMapping(path = "/{no:[0-9]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO register(
            @Parameter(
                    name = "no",
                    in = ParameterIn.PATH,
                    description = "주문 고유번호",
                    required = true,
                    example = "123"
            )
            @PathVariable("no") Long no,
            @RequestBody(
                    description = "배정 요청 DTO (userNo: 기사번호, vehicleNo: 차량번호)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderReqDTO.class),
                            examples = @ExampleObject(
                                    name = "배정 요청 예시",
                                    value = "{ \"userNo\":456, \"vehicleNo\":789 }"
                            )
                    )
            )
            @Valid OrderReqDTO orderReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );
}
