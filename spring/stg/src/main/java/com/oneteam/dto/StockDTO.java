package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.stock.StockEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

  private Long no;
  private String itemName;
  private Long itemNo;
  private int qty;
  private int bundle;
  private Long fileNo;
  private String regUserName;
  private String modUserName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  public static StockDTO findByStock(StockEntity stockEntity) {
    return (stockEntity == null) ? null : StockDTO.builder()
        .no(stockEntity.getNo())
        .itemName((stockEntity.getItem() == null) ? null : stockEntity.getItem().getName())
        .itemNo(stockEntity.getItemNo())
        .qty(stockEntity.getQty())
        .bundle((stockEntity.getItem() == null) ? null : stockEntity.getItem().getBundle())
        .fileNo((stockEntity.getItem() == null) ? null : stockEntity.getItem().getFileNo())
        .regDate(stockEntity.getRegDate())
        .regUserName((stockEntity.getRegUser() == null) ? null : stockEntity.getRegUser().getName())
        .modDate(stockEntity.getModDate())
        .modUserName((stockEntity.getModUser() == null) ? null : stockEntity.getModUser().getName())
        .build();
  }

  public static Map<String, Object> findByStocks(Page<StockEntity> stockEntitys) {
    Map<String, Object> resultMap = new HashMap<>();
    List<StockDTO> stocks = new ArrayList<>();
    stockEntitys.forEach(stock -> stocks.add(StockDTO.findByStock(stock)));
    resultMap.put("list", stocks);
    resultMap.put("totalElements", stockEntitys.getTotalElements());
    resultMap.put("totalPages", stockEntitys.getTotalPages());
    resultMap.put("size", stockEntitys.getSize());
    return resultMap;
  }

}
