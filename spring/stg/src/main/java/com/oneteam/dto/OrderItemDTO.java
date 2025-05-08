package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.order.OrderItemEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주 품목 응답 데이터")
public class OrderItemDTO {

  private Long no;
  private Long orderNo;
  private Long itemNo;
  private String itemName;
  private int qty;
  private int price;
  private int bundle;
  private int outQty;
  private int inQty;
  private String regUserName;
  private String modUserName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  public static OrderItemDTO findByOrderItem(OrderItemEntity orderItemEntity) {
    return (orderItemEntity == null) ? null : OrderItemDTO.builder()
        .no(orderItemEntity.getNo())
        .orderNo(orderItemEntity.getOrderNo())
        .itemNo(orderItemEntity.getItemNo())
        .itemName((orderItemEntity.getItem() == null) ? null : orderItemEntity.getItem().getName())
        .qty(orderItemEntity.getQty())
        .price(orderItemEntity.getPrice())
        .bundle((orderItemEntity.getItem() == null) ? 0 : orderItemEntity.getItem().getBundle())
        .regDate(orderItemEntity.getRegDate())
        .regUserName((orderItemEntity.getRegUser() == null) ? null : orderItemEntity.getRegUser().getName())
        .modDate(orderItemEntity.getModDate())
        .modUserName((orderItemEntity.getModUser() == null) ? null : orderItemEntity.getModUser().getName())
        .build();
  }

  public static Map<String, Object> findByOrderItems(Page<OrderItemEntity> orderItemEntities) {
    Map<String, Object> resultMap = new HashMap<>();
    List<OrderItemDTO> orderItems = new ArrayList<>();
    orderItemEntities.forEach(orderItem -> orderItems.add(OrderItemDTO.findByOrderItem(orderItem)));
    resultMap.put("list", orderItems);
    resultMap.put("totalElements", orderItemEntities.getTotalElements());
    resultMap.put("totalPages", orderItemEntities.getTotalPages());
    resultMap.put("size", orderItemEntities.getSize());
    return resultMap;
  }

  public static List<OrderItemDTO> findByOrderItems(List<OrderItemEntity> orderItemEntities) {
    List<OrderItemDTO> orderItems = new ArrayList<>();
    orderItemEntities.forEach(orderItem -> orderItems.add(OrderItemDTO.findByOrderItem(orderItem)));
    return orderItems;
  }

}
