package com.oneteam.item;

import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController implements ItemControllerDocs {

  private final ItemService itemService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResDTO findAll(@PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(name = "no", required = false) String no, @RequestParam(name = "name", required = false) String name) {
    return itemService.findAll(pageable, no, name);
  }

//  @PreAuthorize("isAuthenticated()")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
    return itemService.findByNo(no, authentication);
  }

//  @PreAuthorize("isAuthenticated()")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PutMapping
  public ResDTO register(@ModelAttribute @Valid ItemReqDTO itemReqDTO, Authentication authentication) {
    return itemService.register(itemReqDTO, authentication);
  }

//  @PreAuthorize("isAuthenticated()")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PatchMapping("/{no:[0-9]+}")
  public ResDTO modify(@PathVariable("no") Long no, @ModelAttribute @Valid ItemReqDTO itemReqDTO, Authentication authentication) {
    return itemService.modify(no, itemReqDTO, authentication);
  }

//  @PreAuthorize("isAuthenticated()")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @DeleteMapping("/{no:[0-9]+}")
  public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
    return itemService.delete(no, authentication);
  }

}
