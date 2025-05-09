package com.oneteam.release;

import com.oneteam.dto.ReleaseReqDTO;
import com.oneteam.dto.ResDTO;
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
@RequestMapping("/release")
@RequiredArgsConstructor
public class ReleaseController {

  private final ReleaseService releaseService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResDTO findAll(@PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
    return releaseService.findAll(pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
    return releaseService.findByNo(no, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PutMapping
  public ResDTO register(@RequestBody @Valid ReleaseReqDTO releaseReqDTO, Authentication authentication) {
    return releaseService.register(releaseReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PatchMapping("/{no:[0-9]+}")
  public ResDTO modify(@PathVariable("no") Long no, @RequestBody @Valid ReleaseReqDTO releaseReqDTO, Authentication authentication) {
    return releaseService.modify(no, releaseReqDTO, authentication);
  }

}
