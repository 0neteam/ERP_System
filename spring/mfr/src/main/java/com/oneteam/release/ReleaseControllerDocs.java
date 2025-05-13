package com.oneteam.release;

import com.oneteam.dto.ReleaseItemReqDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "입출고관리", description = "Release 입출고 API")
@RequestMapping("/release")
public interface ReleaseControllerDocs {

    @Operation(summary = "입출고 목록 조회",
            description = "입출고 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"list\":[{...}],\"totalElements\":10,\"totalPages\":2,\"size\":5},\"message\":null}"
                            )
                    )
            )
    })
    @GetMapping
    ResDTO findAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC)
            Pageable pageable
    );

    @Operation(summary = "운송번호로 입출고 조회",
            description = "운송번호(transpNo)에 해당하는 입출고 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":[...],\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "데이터 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시",
                                    value = "{\"status\":false,\"result\":null,\"message\":\"조회된 입출고가 없습니다.\"}"
                            )
                    )
            )
    })
    @GetMapping("/{transpNo}")
    ResDTO findByTranspNo(
            @Parameter(in = ParameterIn.PATH, description = "운송번호", example = "123")
            @PathVariable("transpNo") Long transpNo,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(summary = "발주번호로 입출고 페이징 조회",
            description = "발주번호(no)로 입출고 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":{\"list\":[...],\"totalElements\":3,\"totalPages\":1,\"size\":5},\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "데이터 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시",
                                    value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 입출고 입니다.\"}"
                            )
                    )
            )
    })
    @PostMapping("/{no}")
    ResDTO findByNo(
            @Parameter(in = ParameterIn.PATH, description = "발주번호", example = "10")
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            Authentication authentication,
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC)
            Pageable pageable10
    );

    @Operation(summary = "입출고 등록",
            description = "발주번호(no)에 대해 입출고를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":null,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @RequestBody(
            description = "입출고 등록 정보: releases 리스트",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReleaseItemReqDTO.class),
                    examples = @ExampleObject(
                            name = "등록 요청 예시",
                            value = "{\"releases\":[{\"orderItemNo\":1,\"itemNo\":100,\"oQty\":50}]}"
                    )
            )
    )
    @PutMapping(path = "/{no}")
    ResDTO register(
            @Parameter(in = ParameterIn.PATH, description = "발주번호", example = "10")
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            @Valid ReleaseItemReqDTO releaseItemReqDTO,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(summary = "입출고 수정",
            description = "운송번호(transpNo)에 해당하는 입출고를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "성공 예시",
                                    value = "{\"status\":true,\"result\":null,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "데이터 없음")
    })
    @RequestBody(
            description = "입출고 수정 정보: releases 리스트",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReleaseItemReqDTO.class),
                    examples = @ExampleObject(
                            name = "수정 요청 예시",
                            value = "{\"releases\":[{\"no\":5,\"iQty\":45}]}"
                    )
            )
    )
    @PatchMapping(path = "/{transpNo}")
    ResDTO modify(
            @Parameter(in = ParameterIn.PATH, description = "운송번호", example = "123")
            @PathVariable("transpNo") Long transpNo,
            @Parameter(hidden = true)
            @Valid ReleaseItemReqDTO releaseItemReqDTO,
            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(summary = "입출고 삭제",
            description = "입출고번호(no)에 해당하는 입출고를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResDTO.class),
                            examples = @ExampleObject(
                                    name = "삭제 성공 예시",
                                    value = "{\"status\":true,\"result\":null,\"message\":null}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "데이터 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시",
                                    value = "{\"status\":false,\"result\":null,\"message\":\"존재하지 않는 입출고 입니다.\"}"
                            )
                    )
            )
    })
    @DeleteMapping(path = "/{no}")
    ResDTO delete(
            @Parameter(in = ParameterIn.PATH, description = "삭제할 입출고번호", example = "10")
            @PathVariable("no") Long no,
            @Parameter(hidden = true)
            Authentication authentication
    );
}
