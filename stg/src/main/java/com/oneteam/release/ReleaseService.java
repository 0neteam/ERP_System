package com.oneteam.release;

import com.oneteam.dto.ReleaseReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ReleaseService {

  public ResDTO findAll(Pageable pageable);
  public ResDTO findByNo(Long no, Authentication authentication);
  public ResDTO register(ReleaseReqDTO releaseReqDTO, Authentication authentication);
  public ResDTO modify(Long no, ReleaseReqDTO releaseReqDTO, Authentication authentication);

}
