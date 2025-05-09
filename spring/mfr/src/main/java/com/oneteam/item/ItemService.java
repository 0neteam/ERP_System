package com.oneteam.item;

import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ItemService {

  public ResDTO findAll(Pageable pageable, String no, String name);
  public ResDTO findByNo(Long no, Authentication authentication);
  public ResDTO register(ItemReqDTO itemReqDTO, Authentication authentication);
  public ResDTO modify(Long no, ItemReqDTO itemReqDTO, Authentication authentication);
  public ResDTO delete(Long no, Authentication authentication);

}
