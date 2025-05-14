package com.oneteam.stock;

import com.oneteam.dto.ResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "재고 관리", description = "재고 조회 API")
@RequestMapping("/stock")
public interface StockControllerDocs {

    @Operation(summary = "전체 재고 목록 조회 (페이징 및 검색)",
            description = "페이징 처리된 전체 재고 목록을 조회합니다. 품목 번호(itemNo) 또는 품목명(name)으로 검색할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":12,\"totalPages\":1,\"list\":[{\"no\":1,\"itemNo\":100,\"name\":\"테스트 품목\",\"currentQty\":50,\"regUserName\":\"admin\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:30\",\"modDate\":\"null\"}],\"totalElements\":1},\"message\":\"\"}"
                            )
                    )
            )
    })
    @GetMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 12, sort = "no", direction = Sort.Direction.DESC) Pageable pageable,
            @Parameter(in = ParameterIn.QUERY, description = "검색할 품목 번호", example = "100")
            @RequestParam(name = "itemNo", required = false) String itemNo,
            @Parameter(in = ParameterIn.QUERY, description = "검색할 품목명", example = "테스트")
            @RequestParam(name = "name", required = false) String name
    );

    @Operation(summary = "특정 재고 상세 조회 (입출고 내역 포함)",
            description = "재고 번호(no)에 해당하는 재고 상세 정보와 해당 품목의 입출고 내역을 조회합니다 (페이징 처리).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"stock\":{\"no\":1,\"itemNo\":100,\"name\":\"테스트 품목\",\"currentQty\":50,\"regUserName\":\"admin\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:30\",\"modDate\":\"null\"},\"release\":{\"size\":4,\"totalPages\":1,\"list\":[{\"no\":1,\"orderItemNo\":10,\"itemNo\":100,\"oQty\":10,\"iQty\":5,\"pQty\":0,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:15\",\"modDate\":\"null\"},{\"no\":2,\"orderItemNo\":11,\"itemNo\":100,\"oQty\":5,\"iQty\":3,\"pQty\":0,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:20\",\"modDate\":\"null\"}],\"totalElements\":2}},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 재고")
    })
    @PostMapping("/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "조회할 재고 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            @PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable
    );
}