package com.oneteam.stock;

import com.oneteam.dto.ResDTO;
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
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

  private final StockService stockService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public ResDTO findAll(@PageableDefault(size = 12, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(name = "itemNo", required = false) String itemNo, @RequestParam(name = "name", required = false) String name) {
    return stockService.findAll(pageable, itemNo, name);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR')")
  @PostMapping("/{no:[0-9]+}")
  public ResDTO findByNo(@PathVariable("no") Long no, @PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {
    return stockService.findByNo(no, pageable);
  }

}
