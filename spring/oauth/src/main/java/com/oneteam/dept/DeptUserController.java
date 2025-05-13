package com.oneteam.dept;

import com.oneteam.dto.DeptUserReqDTO;
import com.oneteam.dto.ResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dept/user")
@RequiredArgsConstructor
public class DeptUserController implements DeptUserControllerDocs {

  private final DeptUserService deptUserService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/{no:[0-9]+}")
  public ResDTO findAll(@PathVariable("no") Long no, @RequestParam(name = "name", required = false) String name, @PageableDefault(size = 12, sort = "no", direction = Sort.Direction.ASC) Pageable pageable) {
    return deptUserService.findAll(no, name, pageable);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Pageable pageable) {
    return deptUserService.findByNo(no, pageable);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{no:[0-9]+}")
  public ResDTO register(@PathVariable("no") Long no, @RequestBody DeptUserReqDTO deptUserReqDTO, Authentication authentication) {
    return deptUserService.register(no, deptUserReqDTO, authentication);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PatchMapping("/{no:[0-9]+}")
  public ResDTO delete(@PathVariable("no") Long no, @RequestBody DeptUserReqDTO deptUserReqDTO, Authentication authentication) {
    return deptUserService.delete(no, deptUserReqDTO, authentication);
  }

}
