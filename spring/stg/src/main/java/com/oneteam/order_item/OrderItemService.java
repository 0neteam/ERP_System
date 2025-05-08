package com.oneteam.order_item;

import com.oneteam.dto.OrderItemReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderItemService {

  public ResDTO findAll(Pageable pageable);
  public ResDTO findByNo(Long no, Authentication authentication);
  public ResDTO register(OrderItemReqDTO orderItemReqDTO, Authentication authentication);
  public ResDTO modify(OrderItemReqDTO orderItemReqDTO, Authentication authentication);
  public ResDTO delete(OrderItemReqDTO orderItemReqDTO, Authentication authentication);

}
