package com.oneteam.item;

import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.security.core.Authentication;

public interface ItemService {

  public ResDTO register(ItemReqDTO itemReqDTO, Authentication authentication);


}
