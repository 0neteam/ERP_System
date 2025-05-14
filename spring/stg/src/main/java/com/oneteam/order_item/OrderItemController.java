package com.oneteam.order_item;

import com.oneteam.dto.OrderItemReqDTO;
import com.oneteam.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order/item")
@RequiredArgsConstructor
public class OrderItemController implements OrderItemControllerDocs {

  private final OrderItemService orderItemService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResDTO findAll(Pageable pageable) {
    return orderItemService.findAll(pageable);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
    return orderItemService.findByNo(no, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PutMapping
  public ResDTO register(@RequestBody @Valid OrderItemReqDTO orderItemReqDTO, Authentication authentication) {
    return orderItemService.register(orderItemReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PatchMapping
  public ResDTO modify(@RequestBody @Valid OrderItemReqDTO orderItemReqDTO, Authentication authentication) {
    return orderItemService.modify(orderItemReqDTO, authentication);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @DeleteMapping
  public ResDTO delete(@RequestBody @Valid OrderItemReqDTO orderItemReqDTO, Authentication authentication) {
    return orderItemService.delete(orderItemReqDTO, authentication);
  }

}
