package com.oneteam.domain.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

  public Page<FileEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<FileEntity> findByNoAndUseYn(Long no, char useYn);

}
