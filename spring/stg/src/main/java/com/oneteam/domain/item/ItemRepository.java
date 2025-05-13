package com.oneteam.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

  public Page<ItemEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<ItemEntity> findByNoAndUseYn(Long no, char useYn);

}
