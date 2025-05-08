package com.oneteam.order;

import com.oneteam.dto.OrderReqDTO;
import com.oneteam.dto.OrderSearchReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {

  public ResDTO findAll(Pageable pageable, OrderSearchReqDTO orderSearchReqDTO);
  public ResDTO register(Long no, OrderReqDTO orderReqDTO, Authentication authentication);

}
