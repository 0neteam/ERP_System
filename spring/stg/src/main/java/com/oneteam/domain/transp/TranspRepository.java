package com.oneteam.domain.transp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranspRepository extends JpaRepository<TranspEntity, Long> {

  public Page<TranspEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<TranspEntity> findByNoAndUseYn(Long no, char useYn);

}
