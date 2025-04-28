package com.oneteam.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  public Page<OrderEntity> findAllByUseYn(char useYn, Pageable pageable);

  @Query("SELECT o FROM OrderEntity o WHERE o.useYn = :useYn AND CAST(o.no AS string) LIKE %:no%")
  public Page<OrderEntity> findAllByUseYnAndNoContaining(@Param("useYn") char useYn, @Param("no") String no, Pageable pageable);
  public Page<OrderEntity> findAllByUseYnAndStatus(char useYn, int status, Pageable pageable);
  public Page<OrderEntity> findAllByUseYnAndReqDateBetween(char useYn, LocalDateTime startReqDate, LocalDateTime endReqDate, Pageable pageable);
  public Page<OrderEntity> findAllByUseYnAndPerDateBetween(char useYn, LocalDateTime startPerDate, LocalDateTime endPerDate, Pageable pageable);
  public Page<OrderEntity> findAllByUseYnAndCancelDateBetween(char useYn, LocalDateTime startCancelDate, LocalDateTime endCancelDate, Pageable pageable);

  public Optional<OrderEntity> findByNoAndUseYn(Long no, char useYn);

}
