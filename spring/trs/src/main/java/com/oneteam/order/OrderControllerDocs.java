package com.oneteam.order;

import com.oneteam.dto.OrderReqDTO;
import com.oneteam.dto.OrderSearchReqDTO;
import com.oneteam.dto.ResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "운송 기사 관리 (발주 연관)", description = "발주와 관련된 운송 기사 조회 및 등록 API")
@RequestMapping("/order")
public interface OrderControllerDocs {

    @Operation(summary = "운송 기사 목록 조회",
            description = "조건에 따라 운송 기사 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":5,\"totalPages\":1,\"list\":[{\"no\":1,\"userNo\":10,\"userName\":\"홍길동\",\"licence1\":\"1종보통\",\"licence2\":null,\"licence3\":null,\"licence4\":null}],\"totalElements\":1},\"message\":null}"
                            )
                    )
            )
    })
    @PostMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody(description = "운송 기사 검색 조건", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderSearchReqDTO.class),
                            examples = @ExampleObject(
                                    name = "검색 조건 예시",
                                    value = "{\"type\":1,\"name\":\"홍길동\"}"
                            )
                    ))
            @Valid OrderSearchReqDTO orderSearchReqDTO
    );

    @Operation(summary = "특정 발주에 운송 기사 등록",
            description = "특정 발주 번호(no)에 운송 기사를 등록하고, 해당 발주 품목들의 운송 정보를 업데이트하며, 차량 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 차량 또는 기사")
    })
    @PutMapping("/{no}")
    ResDTO register(
            @Parameter(in = ParameterIn.PATH, description = "발주 번호", example = "1")
            @PathVariable("no") Long no,
            @RequestBody(description = "등록할 운송 기사 정보 (차량 번호, 기사 번호)", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderReqDTO.class),
                            examples = @ExampleObject(
                                    name = "등록 요청 예시",
                                    value = "{\"vehicleNo\":10,\"userNo\":5}"
                            )
                    ))
            @Valid OrderReqDTO orderReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );
}