package com.oneteam.order;

import com.oneteam.domain.order.OrderEntity;
import com.oneteam.domain.order.OrderItemEntity;
import com.oneteam.domain.order.OrderItemRepository;
import com.oneteam.domain.order.OrderRepository;
import com.oneteam.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  @Override
  public ResDTO findAll(Pageable pageable) {
    boolean status = false;
    String message = "조회된 발주가 없습니다.";
    Object result = null;
    Page<OrderEntity> orders = orderRepository.findAllByUseYn('Y' , pageable);
    if(orders != null) {
      status = true;
      message = null;
      result = OrderDTO.findByOrders(orders);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  private boolean checkDate(LocalDateTime start, LocalDateTime end) {
    if(start == null) return false;
    if(end == null) return false;
    return true;
  }

  @Override
  public ResDTO findAll(Pageable pageable, OrderSearchReqDTO orderSearchReqDTO) {
    boolean status = false;
    String message = "조회된 발주가 없습니다.";
    Object result = null;
    Integer type = orderSearchReqDTO.getType();
    String no = orderSearchReqDTO.getOrderNo();
    int state = (orderSearchReqDTO.getStatus() == null) ? 0 : orderSearchReqDTO.getStatus();

    LocalDateTime startReqDate = getLocalDateTime(orderSearchReqDTO.getReqDateStart(), false);
    LocalDateTime endReqDate = getLocalDateTime(orderSearchReqDTO.getReqDateEnd(), true);

    LocalDateTime startPerDate = getLocalDateTime(orderSearchReqDTO.getPerDateStart(), false);
    LocalDateTime endPerDate = getLocalDateTime(orderSearchReqDTO.getPerDateEnd(), true);

    LocalDateTime startCancelDate = getLocalDateTime(orderSearchReqDTO.getCancelDateStart(), false);
    LocalDateTime endCancelDate = getLocalDateTime(orderSearchReqDTO.getCancelDateEnd(), true);

    if(type != null) {
      Page<OrderEntity> orders = null;
      if(type == 1) orders = orderRepository.findAllByUseYnAndNoContaining('Y', no, pageable);
      else if(type == 2) {
        if(state == 0) type = 0;
        else orders = orderRepository.findAllByUseYnAndStatus('Y', state, pageable);
      }
      else if(type == 3) {
        if(checkDate(startReqDate, endReqDate))
          orders = orderRepository.findAllByUseYnAndReqDateBetween('Y', startReqDate, endReqDate, pageable);
        else type = 0;
      }
      else if(type == 4) {
        if(checkDate(startPerDate, endPerDate))
          orders = orderRepository.findAllByUseYnAndPerDateBetween('Y', startPerDate, endPerDate, pageable);
        else type = 0;
      }
      else if(type == 5) {
        if(checkDate(startCancelDate, endCancelDate))
          orders = orderRepository.findAllByUseYnAndCancelDateBetween('Y', startCancelDate, endCancelDate, pageable);
        else type = 0;
      }

      if(type == 0) orders = orderRepository.findAllByUseYn('Y' , pageable);

      if(orders != null) {
        status = true;
        message = null;
        result = OrderDTO.findByOrders(orders);
      }
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Authentication authentication, Pageable pageable) {
    boolean status = false;
    String message = "존재하지 않는 발주 입니다.";
    Object result = null;
    OrderEntity orderEntity = orderRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 발주 입니다."));
    if(orderEntity.getNo() > 0) {
      status = true;
      message = null;
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("order", OrderDTO.findByOrder(orderEntity));
      Page<OrderItemsDTO> orderItems = orderItemRepository.findAllByUseYnAndItem('Y', orderEntity.getNo(), pageable);
      orderItems.forEach(System.out::println);
      if(orderItems != null) {
        resultMap.put("items", orderItems);
      }
      result = resultMap;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Authentication authentication) {
    boolean status = false;
    String message = "존재하지 않는 발주 입니다.";
    Object result = null;
    List<OrderItemEntity> orderItems = orderItemRepository.findByOrderNoAndUseYn(no, 'Y');
    if(orderItems != null) {
      status = true;
      message = null;
      result = OrderItemDTO.findByOrderItems(orderItems);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO register(OrderReqDTO orderReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 발주가 등록 되지 않았습니다.";
    Object result = null;
    OrderEntity order = OrderEntity.builder().status(orderReqDTO.getStatus()).build();
    order.setUseYn('Y');
    order.setRegUserNo(Long.parseLong(authentication.getName()));
    order = orderRepository.save(order);
    if(order.getNo() > 0) {
      int cnt = 0;
      for(OrderItemDTO item : orderReqDTO.getItems()) {
        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
            .orderNo(order.getNo())
            .itemNo(item.getItemNo())
            .qty(item.getQty())
            .price(item.getPrice())
            .build();
        orderItemEntity.setUseYn('Y');
        orderItemEntity.setRegUserNo(Long.parseLong(authentication.getName()));
        orderItemEntity = orderItemRepository.save(orderItemEntity);
        if(orderItemEntity.getNo() > 0) cnt++;
      }
      if(cnt == orderReqDTO.getItems().size()) {
        status = true;
        message = null;
      }
    }
    return ResDTO.builder().status(status).message(message).build();
  }

  @Transactional
  private boolean modifyItem(List<OrderItemEntity> orderItems, OrderItemDTO item, Long userNo) {
    boolean check = true;
    for(OrderItemEntity orderItemEntity : orderItems) {
      if(orderItemEntity.getItemNo() == item.getItemNo()) {
        check = false;
        orderItemEntity.setQty(item.getQty());
        orderItemEntity.setUseYn('Y');
        orderItemEntity.setModUserNo(userNo);
        orderItemRepository.save(orderItemEntity);
        break;
      }
    }
    return check;
  }

  @Transactional
  @Override
  public ResDTO modify(Long no, OrderReqDTO orderReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 발주가 수정 되지 않았습니다.";
    Object result = null;
    List<OrderItemEntity> orderItems = orderItemRepository.findByOrderNo(no);
    List<OrderItemDTO> items = orderReqDTO.getItems();
    Long userNo = Long.parseLong(authentication.getName());
    int cnt = 0;
    for(OrderItemEntity orderItemEntity : orderItems) {
      orderItemEntity.setUseYn('N');
      orderItemEntity.setModUserNo(userNo);
      orderItemRepository.save(orderItemEntity);
    }
    for(OrderItemDTO item : items) {
      if(modifyItem(orderItems, item, userNo)) {
        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
            .orderNo(no)
            .itemNo(item.getItemNo())
            .qty(item.getQty())
            .price(item.getPrice())
            .build();
        orderItemEntity.setUseYn('Y');
        orderItemEntity.setRegUserNo(Long.parseLong(authentication.getName()));
        orderItemRepository.save(orderItemEntity);
      }
      cnt++;
    }
    if(cnt == items.size()) {
      status = true;
      message = null;
    }
    return ResDTO.builder().status(status).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO delete(Long no, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 발주가 삭제 되지 않았습니다.";
    Object result = null;
    OrderEntity order = orderRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 발주 입니다."));
    order.setStatus(4);
    order.setModUserNo(Long.parseLong(authentication.getName()));
    order.setCancelDate(LocalDateTime.now());
    order = orderRepository.save(order);
    if(order.getNo() > 0) {
      status = true;
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  private LocalDateTime getLocalDateTime(String data, boolean type) {
    if(data == null) return null;
    String time = "T00:00:00";
    if(type) time = "T23:59:59";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    return LocalDateTime.parse(data + time, formatter);
  }



}
