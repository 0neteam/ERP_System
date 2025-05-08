package com.oneteam.stock;

import com.oneteam.dto.ResDTO;
import org.springframework.data.domain.Pageable;

public interface StockService {

  public ResDTO findAll(Pageable pageable, String itemNo, String name);
  public ResDTO findByNo(Long no, Pageable pageable);

}
