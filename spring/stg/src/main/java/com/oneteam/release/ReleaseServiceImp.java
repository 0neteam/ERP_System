package com.oneteam.release;

import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.release.ReleaseRepository;
import com.oneteam.dto.ReleaseDTO;
import com.oneteam.dto.ReleaseReqDTO;
import com.oneteam.dto.ResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReleaseServiceImp implements ReleaseService {

  private final ReleaseRepository releaseRepository;

  @Override
  public ResDTO findAll(Pageable pageable) {
    boolean status = false;
    String message = "조회된 입출고가 없습니다.";
    Object result = null;
    Page<ReleaseEntity> releases = releaseRepository.findAllByUseYn('Y', pageable);
    if(releases != null) {
      status = true;
      message = null;
      result = ReleaseDTO.findByReleases(releases);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Authentication authentication) {
    boolean status = false;
    String message = "존재하지 않는 입출고 입니다.";
    Object result = null;
    ReleaseEntity release = releaseRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 입출고 입니다."));
    if(release.getNo() > 0) {
      status = true;
      message = null;
      result = ReleaseDTO.findByRelease(release);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO register(ReleaseReqDTO releaseReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 입출고가 등록 되지 않았습니다.";
    Object result = null;
    ReleaseEntity release = ReleaseEntity.builder()
        .orderItemNo(releaseReqDTO.getOrderItemNo())
        .itemNo(releaseReqDTO.getItemNo())
        .oQty(releaseReqDTO.getOQty())
        .build();
    release.setUseYn('Y');
    release.setRegUserNo(Long.parseLong(authentication.getName()));
    release = releaseRepository.save(release);
    if(release.getNo() > 0) {
      status = true;
      message = null;
      result = ReleaseDTO.findByRelease(release);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO modify(Long no, ReleaseReqDTO releaseReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 입출고가 수정 되지 않았습니다.";
    Object result = null;
    ReleaseEntity release = releaseRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 입출고 입니다."));
    release.setOQty(releaseReqDTO.getOQty());
    release.setIQty(releaseReqDTO.getIQty());
    release.setPQty(releaseReqDTO.getPQty());
    release.setModUserNo(Long.parseLong(authentication.getName()));
    release = releaseRepository.save(release);
    if(release.getNo() > 0) {
      status = true;
      message = null;
      result = ReleaseDTO.findByRelease(release);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

}
