package com.oneteam.release;

import com.oneteam.dto.ReleaseItemReqDTO;
import com.oneteam.dto.ReleaseReqDTO;
import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ReleaseService {

  public ResDTO findAll(Pageable pageable);
  public ResDTO findByNo(Long no, Authentication authentication, Pageable pageable);
  public ResDTO register(Long no, ReleaseItemReqDTO releaseItemReqDTO, Authentication authentication);
  public ResDTO modify(Long transpNo, ReleaseItemReqDTO releaseItemReqDTO, Authentication authentication);
  public ResDTO delete(Long no, Authentication authentication);
  public ResDTO findByTranspNo(Long transpNo, Authentication authentication);

}
