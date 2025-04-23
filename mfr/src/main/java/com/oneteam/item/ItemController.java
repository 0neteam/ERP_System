package com.oneteam.item;

import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController implements ItemControllerDocs {

  private final ItemService itemService;

//  @PreAuthorize("isAuthenticated()")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
  @PutMapping
  public ResDTO register(@ModelAttribute @Valid ItemReqDTO itemReqDTO, Authentication authentication) {
    return itemService.register(itemReqDTO, authentication);
  }
}
