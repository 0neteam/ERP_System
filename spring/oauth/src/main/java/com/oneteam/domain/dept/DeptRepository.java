package com.oneteam.domain.dept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeptRepository extends JpaRepository<DeptEntity, Long> {

  public Page<DeptEntity> findAllByUseYn(char useYn, Pageable pageable);
  public List<DeptEntity> findAllByUseYn(char useYn);
  public Optional<DeptEntity> findByNoAndUseYn(Long no, char useYn);

}
