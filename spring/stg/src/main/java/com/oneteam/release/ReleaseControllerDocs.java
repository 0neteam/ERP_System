package com.oneteam.release;

import com.oneteam.dto.ReleaseReqDTO;
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

@Tag(name = "입출고 관리", description = "입출고 CRUD API")
@RequestMapping("/release")
public interface ReleaseControllerDocs {

    @Operation(summary = "전체 입출고 목록 조회 (페이징)",
            description = "페이징 처리된 전체 입출고 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"size\":5,\"totalPages\":1,\"list\":[{\"no\":1,\"orderItemNo\":10,\"itemNo\":100,\"oQty\":10,\"iQty\":5,\"pQty\":0,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:15\",\"modDate\":\"null\"}],\"totalElements\":1},\"message\":null}"
                            )
                    )
            )
    })
    @GetMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable
    );

    @Operation(summary = "특정 입출고 상세 조회",
            description = "입출고 번호(no)에 해당하는 입출고 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"no\":1,\"orderItemNo\":10,\"itemNo\":100,\"oQty\":10,\"iQty\":5,\"pQty\":0,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:15\",\"modDate\":\"null\"},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 입출고")
    })
    @PostMapping("/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "조회할 입출고 번호", example = "1")
            @PathVariable("no") Long no,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "입출고 등록",
            description = "새로운 입출고 정보를 등록합니다. 요청 본문에 입출고 관련 정보를 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"no\":2,\"orderItemNo\":11,\"itemNo\":200,\"oQty\":5,\"iQty\":0,\"pQty\":0,\"regUserName\":\"test\",\"modUserName\":null,\"regDate\":\"2025-05-13 18:20\",\"modDate\":\"null\"},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO register(
            @RequestBody(description = "등록할 입출고 정보", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReleaseReqDTO.class),
                            examples = @ExampleObject(
                                    name = "등록 요청 예시",
                                    value = "{\"orderItemNo\":11,\"itemNo\":200,\"oQty\":5}"
                            )
                    ))
            @Valid ReleaseReqDTO releaseReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(summary = "특정 입출고 정보 수정",
            description = "입출고 번호(no)에 해당하는 입출고 정보를 수정합니다. 요청 본문에 수정할 입출고 관련 정보를 JSON 형태로 전달합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"no\":1,\"orderItemNo\":10,\"itemNo\":100,\"oQty\":10,\"iQty\":7,\"pQty\":2,\"regUserName\":\"test\",\"modUserName\":\"test\",\"regDate\":\"2025-05-13 18:15\",\"modDate\":\"2025-05-13 18:25\"},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 입출고")
    })
    @PatchMapping(path = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResDTO modify(
            @Parameter(in = ParameterIn.PATH, description = "수정할 입출고 번호", example = "1")
            @PathVariable("no") Long no,
            @RequestBody(description = "수정할 입출고 정보", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReleaseReqDTO.class),
                            examples = @ExampleObject(
                                    name = "수정 요청 예시",
                                    value = "{\"oQty\":10,\"iQty\":7,\"pQty\":2}"
                            )
                    ))
            @Valid ReleaseReqDTO releaseReqDTO,
            @Parameter(hidden = true) Authentication authentication
    );
}
