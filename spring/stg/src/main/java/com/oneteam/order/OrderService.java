package com.oneteam.order;

import com.oneteam.dto.OrderReqDTO;
import com.oneteam.dto.OrderSearchReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {

  public ResDTO findAll(Pageable pageable);
  public ResDTO findAll(Pageable pageable, OrderSearchReqDTO orderSearchReqDTO);
  public ResDTO findByNo(Long no, Authentication authentication, Pageable pageable);
  public ResDTO findByNo(Long no, Authentication authentication);
  public ResDTO register(OrderReqDTO orderReqDTO, Authentication authentication);
  public ResDTO modify(Long no, OrderReqDTO orderReqDTO, Authentication authentication);
  public ResDTO delete(Long no, Authentication authentication);

}
