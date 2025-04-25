package com.oneteam.release;

import com.oneteam.domain.order.OrderEntity;
import com.oneteam.domain.order.OrderItemRepository;
import com.oneteam.domain.order.OrderRepository;
import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.release.ReleaseRepository;
import com.oneteam.domain.stock.StockEntity;
import com.oneteam.domain.stock.StockRepository;
import com.oneteam.domain.transp.TranspEntity;
import com.oneteam.domain.transp.TranspRepository;
import com.oneteam.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReleaseServiceImp implements ReleaseService {

  private final ReleaseRepository releaseRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final TranspRepository transpRepository;
  private final StockRepository stockRepository;

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
  public ResDTO findByTranspNo(Long transpNo, Authentication authentication) {
    boolean status = false;
    String message = "조회된 입출고가 없습니다.";
    Object result = null;
    List<ReleaseEntity> releaseInfo = releaseRepository.findAllByTranspNoAndUseYn(transpNo, 'Y');
    TranspEntity transpInfo = transpRepository.findByNoAndUseYn(transpNo, 'Y')
        .orElseThrow(() -> new RuntimeException("존재하지 않는 운송번호입니다."));
    TranspDTO transp = TranspDTO.findByTransp(transpInfo);
    Map<String, Object> releases = ReleaseDTO.findByReleases(releaseInfo);
    List<Object> resultList = new ArrayList<>();
    resultList.add(transp);
    resultList.add(releases);
    if(releases != null) {
      status = true;
      message = null;
      result = resultList;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Authentication authentication, Pageable pageable) {
    boolean status = false;
    String message = "존재하지 않는 입출고 입니다.";
    Object result = null;
    Page<ReleaseResDTO> releases = releaseRepository.findAllByOrderNoAndUseYn(no, 'Y', pageable);
    if(releases != null) {
      status = true;
      message = null;
      result = releases;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO register(Long no, ReleaseItemReqDTO releaseItemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 입출고가 등록 되지 않았습니다.";
    Object result = null;
    List<ReleaseReqDTO> releases = releaseItemReqDTO.getReleases();
    if(releases != null) {
      for(ReleaseReqDTO releaseReqDTO : releases) {
        ReleaseEntity release = ReleaseEntity.builder()
            .orderItemNo(releaseReqDTO.getOrderItemNo())
            .itemNo(releaseReqDTO.getItemNo())
            .oQty(releaseReqDTO.getOQty())
            .status(0)
            .build();
        release.setUseYn('Y');
        release.setRegUserNo(Long.parseLong(authentication.getName()));
        releaseRepository.save(release);
      }
      OrderEntity orderEntity = orderRepository.findByNoAndUseYn(no,'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 발주 입니다."));
      orderEntity.setStatus(2);
      orderEntity.setModUserNo(Long.parseLong(authentication.getName()));
      orderEntity = orderRepository.save(orderEntity);
      if(orderEntity.getNo() > 0) {
        status = true;
        message = null;
      }
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO modify(Long transpNo, ReleaseItemReqDTO releaseItemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 입출고가 수정 되지 않았습니다.";
    Object result = null;
    List<ReleaseEntity> ReleaseEntitys = releaseRepository.findAllByTranspNoAndUseYn(transpNo, 'Y');

    try{
      if(releaseItemReqDTO.getPoint()==1) {
        for (ReleaseEntity release : ReleaseEntitys) {
          release.setDepDate(LocalDateTime.now());
          release.setModUserNo(Long.parseLong(authentication.getName()));
          releaseRepository.save(release);
        }
      }
      if(releaseItemReqDTO.getPoint()==2) {
        List<ReleaseReqDTO> releases = releaseItemReqDTO.getReleases();
        for(ReleaseReqDTO release : releases) {
          ReleaseEntity releaseEntity = releaseRepository.findByNoAndUseYn(release.getNo(), 'Y').orElseThrow(() -> new RuntimeException("임지가 잘못 했다!!"));
          releaseEntity.setArrDate(LocalDateTime.now());
          releaseEntity.setIQty(release.getIQty());
          StockEntity stockEntity = stockRepository.findByItemNo(release.getItemNo());
          if(stockEntity==null){
            stockEntity = StockEntity.builder()
                .itemNo(release.getItemNo())
                .qty(release.getIQty())
                .build();
            stockEntity.setUseYn('Y');
            stockEntity.setRegUserNo(Long.parseLong(authentication.getName()));
            stockRepository.save(stockEntity);
          } else {
            int qty = stockEntity.getQty();
            stockEntity.setQty(qty + release.getIQty());
            stockEntity.setModUserNo(Long.parseLong(authentication.getName()));

          }

          if((release.getOQty()-release.getIQty()) > 0) {
            releaseEntity.setPQty(release.getOQty()-release.getIQty());
            releaseEntity.setStatus(2);
          } else {
            releaseEntity.setStatus(1);
          }
          releaseEntity.setModUserNo(Long.parseLong(authentication.getName()));
          releaseRepository.save(releaseEntity);
        }

        OrderCntDTO orderCntDTO = orderItemRepository.findByOrderCnt(releaseItemReqDTO.getOrderNo(), releaseItemReqDTO.getOrderNo()).orElseThrow(() -> new RuntimeException("임지가 했어!!"));
        if(new BigDecimal("0").equals(orderCntDTO.getCnt())) { // 완료
          OrderEntity orderEntity = orderRepository.findByNoAndUseYn(orderCntDTO.getOrderNo(), 'Y').orElseThrow(() -> new RuntimeException("임지가 했어!!"));
          orderEntity.setStatus(3);
          orderEntity.setPerDate(LocalDateTime.now());
          orderEntity.setModUserNo(Long.parseLong(authentication.getName()));
          orderRepository.save(orderEntity);
        }

      }

      status = true;
      message = null;

    } catch (Exception e) {
      e.printStackTrace();
    }
      return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO delete(Long no, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 입출고가 삭제 되지 않았습니다.";
    Object result = null;
    ReleaseEntity release = releaseRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 입출고 입니다."));
    release.setUseYn('N');
    release.setModUserNo(Long.parseLong(authentication.getName()));
    release = releaseRepository.save(release);
    if(release.getNo() > 0) {
      status = true;
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }


}
