package com.oneteam.dept;

import com.oneteam.dto.DeptUserReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface DeptUserService {

  public ResDTO findAll(Long no, String name, Pageable pageable);
  public ResDTO findByNo(Long no, Pageable pageable);
  public ResDTO register(Long no, DeptUserReqDTO deptUserReqDTO, Authentication authentication);
  public ResDTO delete(Long no, DeptUserReqDTO deptUserReqDTO, Authentication authentication);

}
