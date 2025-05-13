package com.oneteam.dept;

import com.oneteam.dto.DeptReqDTO;
import com.oneteam.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController implements DeptControllerDocs {

  private final DeptService deptService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public ResDTO findAll() {
    return deptService.findAll();
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
    return deptService.findByNo(no, authentication);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping()
  public ResDTO register(@RequestBody @Valid DeptReqDTO deptReqDTO, Authentication authentication) {
    return deptService.register(deptReqDTO, authentication);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{no:[0-9]+}")
  public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
    return deptService.delete(no, authentication);
  }

}
