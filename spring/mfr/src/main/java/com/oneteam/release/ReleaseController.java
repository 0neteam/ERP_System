package com.oneteam.release;

import com.oneteam.dto.ReleaseItemReqDTO;
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
public class ReleaseController implements ReleaseControllerDocs {

  private final ReleaseService releaseService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResDTO findAll(@PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
    return releaseService.findAll(pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @GetMapping("/{transpNo:[0-9]+}")
  public ResDTO findByTranspNo(@PathVariable("transpNo") Long transpNo, Authentication authentication) {
    return releaseService.findByTranspNo(transpNo, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication, @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
    return releaseService.findByNo(no, authentication, pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PutMapping("/{no:[0-9]+}")
  public ResDTO register(@PathVariable("no") Long no, @RequestBody @Valid ReleaseItemReqDTO releaseItemReqDTO, Authentication authentication) {
    return releaseService.register(no, releaseItemReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PatchMapping("/{transpNo:[0-9]+}")
  public ResDTO modify(@PathVariable("transpNo") Long transpNo, @RequestBody @Valid ReleaseItemReqDTO releaseItemReqDTO, Authentication authentication) {
    return releaseService.modify(transpNo, releaseItemReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @DeleteMapping("/{no:[0-9]+}")
  public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
    return releaseService.delete(no, authentication);
  }

}
