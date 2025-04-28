package com.oneteam.dept;

import com.oneteam.dto.DeptReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.security.core.Authentication;

public interface DeptService {

  public ResDTO findAll();
  public ResDTO findByNo(Long no, Authentication authentication);
  public ResDTO register(DeptReqDTO deptReqDTO, Authentication authentication);
  public ResDTO delete(Long no, Authentication authentication);

}
