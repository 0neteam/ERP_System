package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.item.ItemEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "품목 응답 데이터")
public class ItemDTO {

  private Long no;
  private String name;
  private int bundle;
  private int price;
  private Long fileNo;
  private String regUserName;
  private String modUserName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  public static ItemDTO findByItem(ItemEntity itemEntity) {
    return (itemEntity == null) ? null : ItemDTO.builder()
        .no(itemEntity.getNo())
        .name(itemEntity.getName())
        .bundle(itemEntity.getBundle())
        .price(itemEntity.getPrice())
        .fileNo(itemEntity.getFileNo())
        .regDate(itemEntity.getRegDate())
        .regUserName((itemEntity.getRegUser() == null) ? null : itemEntity.getRegUser().getName())
        .modDate(itemEntity.getModDate())
        .modUserName((itemEntity.getModUser() == null) ? null : itemEntity.getModUser().getName())
        .build();
  }

  public static Map<String, Object> findByItems(Page<ItemEntity> itemEntitys) {
    Map<String, Object> resultMap = new HashMap<>();
    List<ItemDTO> items = new ArrayList<>();
    itemEntitys.forEach(item -> items.add(ItemDTO.findByItem(item)));
    resultMap.put("list", items);
    resultMap.put("totalElements", itemEntitys.getTotalElements());
    resultMap.put("totalPages", itemEntitys.getTotalPages());
    resultMap.put("size", itemEntitys.getSize());
    return resultMap;
  }

}
