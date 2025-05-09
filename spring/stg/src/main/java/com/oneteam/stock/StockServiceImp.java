package com.oneteam.stock;

import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.release.ReleaseRepository;
import com.oneteam.domain.stock.StockEntity;
import com.oneteam.domain.stock.StockRepository;
import com.oneteam.dto.ReleaseDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.dto.StockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockServiceImp implements StockService {

  private final StockRepository stockRepository;
  private final ReleaseRepository releaseRepository;

  @Override
  public ResDTO findAll(Pageable pageable, String itemNo, String name) {
    boolean status = false;
    String message = "조회된 재고가 없습니다.";
    Object result = null;
    if(itemNo == null) itemNo = "";
    if(name == null) name = "";
    Page<StockEntity> stockEntitys = stockRepository.findAllByUseYn('Y', itemNo, name, pageable);
    if(stockEntitys.getSize() > 0) {
      status = true;
      message = "";
      result = StockDTO.findByStocks(stockEntitys);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Pageable pageable) {
    boolean status = false;
    String message = "존재하지 않는 재고 입니다.";
    Object result = null;
    StockDTO stockDTO = null;
    Map<String, Object> releaseMap = null;
    StockEntity stock = stockRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 재고 입니다."));
    if(stock.getNo() > 0) {
      status = true;
      message = null;
      stockDTO = StockDTO.findByStock(stock);

      Page<ReleaseEntity> releaseEntities = releaseRepository.findAllByItemNoAndUseYn(stock.getItemNo(), 'Y', pageable);
      if(releaseEntities.getSize() > 0) {
        releaseMap = ReleaseDTO.findByReleases(releaseEntities);
      }
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("stock", stockDTO);
      resultMap.put("release", releaseMap);
      result = resultMap;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

}
