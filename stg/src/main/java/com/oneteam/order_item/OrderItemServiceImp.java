package com.oneteam.order_item;

import com.oneteam.domain.order.OrderItemEntity;
import com.oneteam.domain.order.OrderItemRepository;
import com.oneteam.dto.OrderItemDTO;
import com.oneteam.dto.OrderItemReqDTO;
import com.oneteam.dto.ResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemServiceImp implements OrderItemService {

  private final OrderItemRepository orderItemRepository;

  @Override
  public ResDTO findAll(Pageable pageable) {
    boolean status = false;
    String message = "조회된 발주품목이 없습니다.";
    Object result = null;
    Page<OrderItemEntity> orderItems = orderItemRepository.findAllByUseYn('Y', pageable);
    if(orderItems != null) {
      status = true;
      message = null;
      result = OrderItemDTO.findByOrderItems(orderItems);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Authentication authentication) {
    boolean status = false;
    String message = "존재하지 않는 발주품목 입니다.";
    Object result = null;
    List<OrderItemEntity> orderItems = orderItemRepository.findByOrderNoAndUseYn(no, 'Y');
    if(orderItems.size() > 0) {
      status = true;
      message = null;
      result = OrderItemDTO.findByOrderItems(orderItems);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO register(OrderItemReqDTO orderItemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 발주품목이 등록 되지 않았습니다.";
    Object result = null;
    int size = 0;
    List<OrderItemDTO> items = orderItemReqDTO.getItems();
    items.forEach(item -> {
      OrderItemEntity orderItem = OrderItemEntity.builder()
          .orderNo(orderItemReqDTO.getOrderNo())
          .itemNo(item.getItemNo())
          .qty(item.getQty())
          .price(item.getPrice())
          .build();
      orderItem.setUseYn('Y');
      orderItem.setRegUserNo(Long.parseLong(authentication.getName()));
      orderItemRepository.save(orderItem);
    });
    List<OrderItemEntity> orderItems = orderItemRepository.findByOrderNoAndUseYn(orderItemReqDTO.getOrderNo(), 'Y');
    if(items.size() == orderItems.size()) {
      status = true;
      message = null;
      result = OrderItemDTO.findByOrderItems(orderItems);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO modify(OrderItemReqDTO orderItemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 발주품목이 수정 되지 않았습니다.";
    Object result = null;
    List<OrderItemDTO> items = orderItemReqDTO.getItems();
    items.forEach(item -> {
      OrderItemEntity orderItem = orderItemRepository.findByNoAndUseYn(item.getNo(), 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 발주품목 입니다."));
      orderItem.setQty(item.getQty());
      orderItem.setPrice(item.getPrice());
      orderItem.setModUserNo(Long.parseLong(authentication.getName()));
      orderItemRepository.save(orderItem);
    });
    List<OrderItemEntity> orderItems = orderItemRepository.findByOrderNo(orderItemReqDTO.getOrderNo());
    if(items.size() == orderItems.size()) {
      status = true;
      message = null;
      result = OrderItemDTO.findByOrderItems(orderItems);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO delete(OrderItemReqDTO orderItemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 발주품목이 삭제 되지 않았습니다.";
    Object result = null;
    List<OrderItemDTO> items = orderItemReqDTO.getItems();
    items.forEach(item -> {
      OrderItemEntity orderItem = orderItemRepository.findByNoAndUseYn(item.getNo(), 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 발주품목 입니다."));
      orderItem.setUseYn('N');
      orderItem.setModUserNo(Long.parseLong(authentication.getName()));
      orderItemRepository.save(orderItem);
    });
    List<OrderItemEntity> orderItems = orderItemRepository.findByOrderNo(orderItemReqDTO.getOrderNo());
    if(items.size() == orderItems.size()) {
      status = true;
      message = null;
      result = OrderItemDTO.findByOrderItems(orderItems);
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }
}
