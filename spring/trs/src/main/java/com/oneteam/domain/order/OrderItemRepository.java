package com.oneteam.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

  public Page<OrderItemEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<OrderItemEntity> findByNoAndUseYn(Long no, char useYn);
  public List<OrderItemEntity> findByOrderNoAndUseYn(Long orderNo, char useYn);
  public List<OrderItemEntity> findByOrderNo(Long orderNo);

}
