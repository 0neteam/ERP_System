package com.oneteam.domain.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

  public Page<StockEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<StockEntity> findByNoAndUseYn(Long no, char useYn);

}
