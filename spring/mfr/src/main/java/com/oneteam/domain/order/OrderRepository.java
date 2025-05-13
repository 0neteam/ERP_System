package com.oneteam.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  public Page<OrderEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<OrderEntity> findByNoAndUseYn(Long no, char useYn);

}
