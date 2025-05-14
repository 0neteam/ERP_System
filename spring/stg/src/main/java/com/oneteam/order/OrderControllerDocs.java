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

@Tag(name = "발주 관리", description = "발주 CRUD API")
@RequestMapping("/order")
public interface OrderControllerDocs {

    @Operation(summary = "전체 발주 목록 조회 (페이징)",
            description = "페이징 처리된 전체 발주 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":5,\"totalPages\":1,\"list\":[{\"no\":1,\"status\":1,\"reqDate\":\"2025-05-13T17:50:00\",\"perDate\":null,\"cancelDate\":null,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 17:50\",\"modDate\":\"null\"}],\"totalElements\":1},\"message\":null}"
                            )
                    )
            )
    })
    @GetMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "조건별 발주 목록 조회 (페이징)",
            description = "조건에 따라 페이징 처리된 발주 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":5,\"totalPages\":1,\"list\":[{\"no\":1,\"status\":1,\"reqDate\":\"2025-05-13T17:50:00\",\"perDate\":null,\"cancelDate\":null,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 17:50\",\"modDate\":\"null\"}],\"totalElements\":1},\"message\":null}"
                            )
                    )
            )
    })
    @PostMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody(description = "발주 검색 조건", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderSearchReqDTO.class),
                            examples = @ExampleObject(
                                    name = "검색 조건 예시",
                                    value = "{\"type\":1,\"orderNo\":\"ORD-001\",\"status\":1,\"reqDateStart\":\"2025-05-01\",\"reqDateEnd\":\"2025-05-10\",\"perDateStart\":\"2025-05-15\",\"perDateEnd\":\"2025-05-20\",\"cancelDateStart\":\"2025-05-22\",\"cancelDateEnd\":\"2025-05-25\"}"
                            )
                    ))
            @Valid OrderSearchReqDTO orderSearchReqDTO
    );

    @Operation(summary = "특정 발주 상세 조회 (페이징된 품목 포함)",
            description = "발주 번호(no)에 해당하는 발주 상세 정보와 해당 발주에 포함된 품목 목록을 조회합니다 (페이징 처리).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"order\":{\"no\":1,\"status\":1,\"reqDate\":\"2025-05-13T17:50:00\",\"perDate\":null,\"cancelDate\":null,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 17:50\",\"modDate\":\"null\"},\"items\":{\"size\":5,\"totalPages\":1,\"list\":[{\"no\":1,\"orderNo\":1,\"itemNo\":100,\"qty\":2,\"price\":500,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 17:50\",\"modDate\":\"null\"}],\"totalElements\":1}},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주")
    })
    @PostMapping("/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "조회할 발주 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication,
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "특정 발주 수정 화면 조회 (품목 목록)",
            description = "발주 번호(no)에 해당하는 발주에 포함된 품목 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":[{\"no\":1,\"orderNo\":1,\"itemNo\":100,\"qty\":2,\"price\":500,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 17:50\",\"modDate\":\"null\"},{\"no\":2,\"orderNo\":1,\"itemNo\":200,\"qty\":1,\"price\":1000,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 17:50\",\"modDate\":\"null\"}],\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주")
    })
    @PostMapping("/edit/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "조회할 발주 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "발주 등록",
            description = "새로운 발주를 등록합니다. 요청 본문에 발주 정보와 품목 목록을 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO register(
            @RequestBody(description = "등록할 발주 정보 (상태 및 품목 목록)", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderReqDTO.class),
                            examples = @ExampleObject(
                                    name = "등록 요청 예시",
                                    value = "{\"status\":1,\"items\":[{\"itemNo\":100,\"qty\":2,\"price\":500},{\"itemNo\":200,\"qty\":1,\"price\":1000}]}"
                            )
                    ))
            @Valid OrderReqDTO orderReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "특정 발주 수정",
            description = "발주 번호(no)에 해당하는 발주 정보를 수정합니다. 요청 본문에 수정할 발주 정보와 품목 목록을 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주")
    })
    @PatchMapping(path = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO modify(
            @Parameter(in = ParameterIn.PATH, description = "수정할 발주 번호", example = "1")
            @PathVariable("no") Long no,
            @RequestBody(description = "수정할 발주 정보 (상태 및 품목 목록)", required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderReqDTO.class),
                            examples = @ExampleObject(
                                    name = "수정 요청 예시",
                                    value = "{\"status\":2,\"items\":[{\"itemNo\":100,\"qty\":3,\"price\":550},{\"itemNo\":300,\"qty\":2,\"price\":1200}]}"
                            )
                    ))
            @Valid OrderReqDTO orderReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "특정 발주 삭제 (상태 변경)",
            description = "발주 번호(no)에 해당하는 발주를 삭제합니다 (useYn 플래그를 'N'으로 변경하고 취소일자를 기록합니다).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":null,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주")
    })
    @DeleteMapping("/{no}")
    ResDTO delete(
            @Parameter(in = ParameterIn.PATH, description = "삭제할 발주 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );
}