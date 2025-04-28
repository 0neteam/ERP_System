package com.oneteam.domain.release;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {

  public Page<ReleaseEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<ReleaseEntity> findByNoAndUseYn(Long no, char useYn);

}
