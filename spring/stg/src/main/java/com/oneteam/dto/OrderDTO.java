package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.order.OrderEntity;
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
@Schema(description = "발주 응답 데이터")
public class OrderDTO {

  private Long no;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime reqDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime perDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime cancelDate;
  private int status;
  private String statusName;
  private String regUserName;
  private String modUserName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  public static OrderDTO findByOrder(OrderEntity orderEntity) {
    return (orderEntity == null) ? null : OrderDTO.builder()
        .no(orderEntity.getNo())
        .reqDate(orderEntity.getReqDate())
        .perDate(orderEntity.getPerDate())
        .cancelDate(orderEntity.getCancelDate())
        .status(orderEntity.getStatus())

        .regDate(orderEntity.getRegDate())
        .regUserName((orderEntity.getRegUser() == null) ? null : orderEntity.getRegUser().getName())
        .modDate(orderEntity.getModDate())
        .modUserName((orderEntity.getModUser() == null) ? null : orderEntity.getModUser().getName())
        .build();
  }

  public static Map<String, Object> findByOrders(Page<OrderEntity> orderEntities) {
    Map<String, Object> resultMap = new HashMap<>();
    List<OrderDTO> orders = new ArrayList<>();
    orderEntities.forEach(order -> orders.add(OrderDTO.findByOrder(order)));
    resultMap.put("list", orders);
    resultMap.put("totalElements", orderEntities.getTotalElements());
    resultMap.put("totalPages", orderEntities.getTotalPages());
    resultMap.put("size", orderEntities.getSize());
    return resultMap;
  }

}
