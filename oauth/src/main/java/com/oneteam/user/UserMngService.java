package com.oneteam.user;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserMngReqDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserMngService {

  public ResDTO findAll(String name, Pageable pageable);
  public ResDTO findByNo(Long no, Authentication authentication);
  public ResDTO modify(Long no, UserMngReqDTO userMngReqDTO, Authentication authentication);
  public ResDTO delete(Long no, Authentication authentication);

}
