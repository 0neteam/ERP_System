package com.oneteam.item;

import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "품목관리", description = "품목 CRUD API")
@RequestMapping("/item")
public interface ItemControllerDocs {

    @Operation(summary = "품목 목록 조회",
            description = "no와 name 파라미터로 품목 목록을 조회합니다. ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":1,\"totalPages\":1,\"list\":[{\"no\":100,\"name\":\"테스트\",\"bundle\":250,\"price\":500,\"fileNo\":null,\"regUserName\":\"테스트\",\"modUserName\":null,\"regDate\":\"2025-04-25 12:31\",\"modDate\":\"null\"}],\"totalElements\":1},\"message\":null}"
                            )
                    )
            ),
    })
    @GetMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC)
            Pageable pageable,
            @Parameter(in = ParameterIn.QUERY, description = "품목 번호", example = "100")
            @RequestParam(name = "no", required = false) String no,
            @Parameter(in = ParameterIn.QUERY, description = "품목명", example = "샘플")
            @RequestParam(name = "name", required = false) String name
    );

    @Operation(summary = "품목 상세 조회",
            description = "no에 해당하는 품목을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"no\":2,\"name\":\"신품목\",\"bundle\":5,\"price\":500,\"fileNo\":2,\"regUserName\":\"테스트\",\"modUserName\":\"null\",\"regDate\":\"2025-04-25 12:17\",\"modDate\":\"null\"},\"message\":null}"
                            )
                    )
            ),

            @ApiResponse(responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시",
                                    value = "{\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"존재하지 않는 품목 입니다.\"}"
                            )
                    )
            )
    })
    @PostMapping("/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "조회할 품목 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(summary = "품목 등록",
            description = "multipart/form-data로 품목을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"no\":2,\"name\":\"신품목\",\"bundle\":5,\"price\":500,\"fileNo\":2,\"regUserName\":\"테스트\",\"modUserName\":\"null\",\"regDate\":\"2025-04-25 12:17\",\"modDate\":\"null\"},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Error:Bad Request")
    })
    @RequestBody(
            description = "폼 데이터: name, bundle, price, file",
            required = true,
            content = @Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(implementation = ItemReqDTO.class),
                    examples = @ExampleObject(
                            name = "폼 데이터 예시",
                            value = "name=샘플품목\nbundle=10\nprice=1000\nfile=@sample.png"
                    )
            )
    )
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResDTO register(
            @Parameter(hidden = true) @ModelAttribute @Valid ItemReqDTO itemReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary     = "품목 수정",
            description = "multipart/form-data로 기존 품목을 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "폼 데이터: name, bundle, price, fileNo, file",
                    required    = true,
                    content     = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema    = @Schema(implementation = ItemReqDTO.class),
                            examples  = @ExampleObject(
                                    name  = "폼 데이터 예시",
                                    value = "fileNo=5\n" +
                                            "name=업데이트품목\n" +
                                            "bundle=20\n" +
                                            "price=2000\n" +
                                            "file=@update.png"
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description  = "수정 성공",
                    content      = @Content(
                            mediaType = "application/json",
                            examples  = @ExampleObject(
                                    name  = "수정 성공 예시",
                                    value = "{\n" +
                                            "  \"status\": true,\n" +
                                            "  \"result\": {\n" +
                                            "    \"no\": 4,\n" +
                                            "    \"name\": \"스웨거 테스트 2\",\n" +
                                            "    \"bundle\": 300,\n" +
                                            "    \"price\": 600,\n" +
                                            "    \"fileNo\": 72,\n" +
                                            "    \"regUserName\": \"관리자\",\n" +
                                            "    \"modUserName\": \"관리자\",\n" +
                                            "    \"regDate\": \"2025-04-25 12:14\",\n" +
                                            "    \"modDate\": \"2025-04-25 12:38\"\n" +
                                            "  },\n" +
                                            "  \"message\": null\n" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description  = "잘못된 요청",
                    content      = @Content(
                            mediaType = "application/json",
                            examples  = @ExampleObject(
                                    name  = "실패 예시",
                                    value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 품목 입니다.\"}"
                            )
                    )
            ),
    })
    @PatchMapping(path = "/{no}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResDTO modify(
            @Parameter(in = ParameterIn.PATH, description = "수정할 품목 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true) @ModelAttribute @Valid ItemReqDTO itemReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );


    @Operation(summary = "품목 삭제",
            description = "no에 해당하는 품목을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "삭제 성공 예시",
                                    value = "{\"status\":true,\"result\":null,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Error: Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "품목 없음 예시",
                                    value = "{\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"존재하지 않는 품목 입니다.\"}"
                            )
                    )
            )
    })
    @DeleteMapping("/{no}")
    ResDTO delete(
            @Parameter(in = ParameterIn.PATH, description = "삭제할 품목 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            Authentication authentication
    );
}
