package com.oneteam.user;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserMngReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/mng")
@RequiredArgsConstructor
public class UserMngController implements UserMngControllerDocs {

  private final UserMngService userMngService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public ResDTO findAll(@RequestParam(name = "name", required = false) String name, @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
    return userMngService.findAll(name, pageable);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
    return userMngService.findByNo(no, authentication);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping("/{no:[0-9]+}")
  public ResDTO modify(@PathVariable("no") Long no, @RequestBody @Valid UserMngReqDTO userMngReqDTO, Authentication authentication) {
    return userMngService.modify(no, userMngReqDTO, authentication);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{no:[0-9]+}")
  public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
    return userMngService.delete(no, authentication);
  }

}
