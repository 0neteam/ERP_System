package com.oneteam.transp;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.TranspReqDTO;
import com.oneteam.dto.TranspSearchReqDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TranspSerivce {

    public ResDTO findAll(Pageable pageable);
    public ResDTO findAll(Pageable pageable, TranspSearchReqDTO transpSearchReqDTO);
    public ResDTO findByNo(Long no, Authentication authentication, Pageable pageable);
    public ResDTO register(Long no, TranspReqDTO transpReqDTO, Authentication authentication);
    public ResDTO modify(Long no, TranspReqDTO transpReqDTO, Authentication authentication);
    public ResDTO delete(Long no, Authentication authentication);

}
