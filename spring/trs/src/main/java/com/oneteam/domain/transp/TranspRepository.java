package com.oneteam.domain.transp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TranspRepository extends JpaRepository<TranspEntity, Long> {

  public Page<TranspEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Page<TranspEntity> findAllByUseYnAndDepDateBetween(char useYn, LocalDateTime startDepDate, LocalDateTime endDepDate, Pageable pageable);
  public Page<TranspEntity> findAllByUseYnAndArrDateBetween(char useYn, LocalDateTime startArrDate, LocalDateTime endArrDate, Pageable pageable);
  public Optional<TranspEntity> findByNoAndUseYn(Long no, char useYn);



}
