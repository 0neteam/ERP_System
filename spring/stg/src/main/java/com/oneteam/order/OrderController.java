package com.oneteam.order;

import com.oneteam.dto.OrderReqDTO;
import com.oneteam.dto.OrderSearchReqDTO;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResDTO findAll(@PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
    return orderService.findAll(pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping
  public ResDTO findAll(@PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, @RequestBody @Valid OrderSearchReqDTO orderSearchReqDTO) {
    return orderService.findAll(pageable, orderSearchReqDTO);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication, @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
    return orderService.findByNo(no, authentication, pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/edit/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
    return orderService.findByNo(no, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PutMapping
  public ResDTO register(@RequestBody @Valid OrderReqDTO orderReqDTO, Authentication authentication) {
    return orderService.register(orderReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PatchMapping("/{no:[0-9]+}")
  public ResDTO modify(@PathVariable("no") Long no, @RequestBody @Valid OrderReqDTO orderReqDTO, Authentication authentication) {
    return orderService.modify(no, orderReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @DeleteMapping("/{no:[0-9]+}")
  public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
    return orderService.delete(no, authentication);
  }

}
