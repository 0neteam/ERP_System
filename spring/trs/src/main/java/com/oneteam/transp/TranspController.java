package com.oneteam.transp;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.TranspReqDTO;
import com.oneteam.dto.TranspSearchReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transp")
@RequiredArgsConstructor
public class TranspController implements TranspControllerDocs {

    private final TranspSerivce transpSerivce;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @GetMapping
    public ResDTO findAll(@PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
        return transpSerivce.findAll(pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PostMapping
    public ResDTO findAll(@PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, @RequestBody @Valid TranspSearchReqDTO transpSearchReqDTO) {
        return transpSerivce.findAll(pageable, transpSearchReqDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PostMapping("/{no:[0-9]+}")
    public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication, @PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
        return transpSerivce.findByNo(no, authentication, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PutMapping("/{no:[0-9]+}")
    public ResDTO register(@PathVariable("no") Long no, @RequestBody @Valid TranspReqDTO transpReqDTO, Authentication authentication) {
        return transpSerivce.register(no, transpReqDTO, authentication);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PatchMapping("/{no:[0-9]+}")
    public ResDTO modify(@PathVariable("no") Long no, @RequestBody @Valid TranspReqDTO transpReqDTO, Authentication authentication) {
        return transpSerivce.modify(no, transpReqDTO, authentication);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @DeleteMapping("/{no:[0-9]+}")
    public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
        return transpSerivce.delete(no, authentication);
    }

}
