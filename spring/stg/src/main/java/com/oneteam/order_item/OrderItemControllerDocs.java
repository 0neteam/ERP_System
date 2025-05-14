package com.oneteam.order_item;

import com.oneteam.dto.OrderItemReqDTO;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "발주 품목 관리", description = "발주 품목 CRUD API")
@RequestMapping("/order/item")
public interface OrderItemControllerDocs {

    @Operation(summary = "전체 발주 품목 목록 조회 (페이징)",
            description = "페이징 처리된 전체 발주 품목 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":2,\"totalPages\":1,\"list\":[{\"no\":1,\"orderNo\":10,\"itemNo\":100,\"qty\":2,\"price\":500,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"null\"},{\"no\":2,\"orderNo\":10,\"itemNo\":200,\"qty\":1,\"price\":1000,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"null\"}],\"totalElements\":2},\"message\":null}"
                            )
                    )
            )
    })
    @GetMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 10) Pageable pageable
    );

    @Operation(summary = "특정 발주에 속한 발주 품목 목록 조회",
            description = "발주 번호(no)에 해당하는 발주에 속한 발주 품목 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":[{\"no\":1,\"orderNo\":10,\"itemNo\":100,\"qty\":2,\"price\":500,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"null\"},{\"no\":2,\"orderNo\":10,\"itemNo\":200,\"qty\":1,\"price\":1000,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"null\"}],\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주 품목")
    })
    @PostMapping("/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "조회할 발주 번호", example = "10")
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "발주 품목 등록 (일괄 등록)",
            description = "특정 발주에 여러 개의 품목을 일괄적으로 등록합니다. 요청 본문에 발주 번호와 품목 목록을 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":[{\"no\":3,\"orderNo\":20,\"itemNo\":300,\"qty\":5,\"price\":1200,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:05\",\"modDate\":\"null\"},{\"no\":4,\"orderNo\":20,\"itemNo\":400,\"qty\":10,\"price\":500,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:05\",\"modDate\":\"null\"}],\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO register(
            @RequestBody(description = "등록할 발주 품목 정보 (발주 번호 및 품목 목록)", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderItemReqDTO.class),
                            examples = @ExampleObject(
                                    name = "등록 요청 예시",
                                    value = "{\"orderNo\":20,\"items\":[{\"itemNo\":300,\"qty\":5,\"price\":1200},{\"itemNo\":400,\"qty\":10,\"price\":500}]}"
                            )
                    ))
            @Valid OrderItemReqDTO orderItemReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "발주 품목 수정 (일괄 수정)",
            description = "여러 개의 발주 품목 정보를 일괄적으로 수정합니다. 요청 본문에 수정할 발주 품목 목록을 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":[{\"no\":1,\"orderNo\":10,\"itemNo\":100,\"qty\":3,\"price\":550,\"regUserName\":\"test\",\"modUserName\":\"test\",\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"2025-05-13 18:10\"},{\"no\":2,\"orderNo\":10,\"itemNo\":200,\"qty\":2,\"price\":1100,\"regUserName\":\"test\",\"modUserName\":\"test\",\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"2025-05-13 18:10\"}],\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주 품목")
    })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO modify(
            @RequestBody(description = "수정할 발주 품목 목록 (no 포함)", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderItemReqDTO.class),
                            examples = @ExampleObject(
                                    name = "수정 요청 예시",
                                    value = "{\"items\":[{\"no\":1,\"orderNo\":10,\"itemNo\":100,\"qty\":3,\"price\":550},{\"no\":2,\"orderNo\":10,\"itemNo\":200,\"qty\":2,\"price\":1100}]}"
                            )
                    ))
            @Valid OrderItemReqDTO orderItemReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "발주 품목 삭제 (일괄 삭제)",
            description = "여러 개의 발주 품목을 일괄적으로 삭제합니다. 요청 본문에 삭제할 발주 품목 목록 (no 포함)을 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":[{\"no\":1,\"orderNo\":10,\"itemNo\":100,\"qty\":3,\"price\":550,\"regUserName\":\"test\",\"modUserName\":\"test\",\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"2025-05-13 18:12\"},{\"no\":2,\"orderNo\":10,\"itemNo\":200,\"qty\":2,\"price\":1100,\"regUserName\":\"test\",\"modUserName\":\"test\",\"regDate\":\"2025-05-13 18:00\",\"modDate\":\"2025-05-13 18:12\"}],\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 발주 품목")
    })
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO delete(
            @RequestBody(description = "삭제할 발주 품목 목록 (no 포함)", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderItemReqDTO.class),
                            examples = @ExampleObject(
                                    name = "삭제 요청 예시",
                                    value = "{\"items\":[{\"no\":1,\"orderNo\":10,\"itemNo\":100},{\"no\":2,\"orderNo\":10,\"itemNo\":200}]}"
                            )
                    ))
            @Valid OrderItemReqDTO orderItemReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );
}