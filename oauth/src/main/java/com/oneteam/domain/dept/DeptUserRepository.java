package com.oneteam.domain.dept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeptUserRepository extends JpaRepository<DeptUserEntity, Long> {

    public Page<DeptUserEntity> findAllByUseYn(char useYn, Pageable pageable);
    public Page<DeptUserEntity> findAllByDeptNoAndUseYn(Long deptNo, char useYn, Pageable pageable);
    public Page<DeptUserEntity> findAllByNoNotAndUseYn(Long no, char useYn, Pageable pageable);
    public Optional<DeptUserEntity> findByNoAndUseYn(Long no, char useYn);
    public DeptUserEntity findByDeptNoAndUserNo(Long deptNo, Long userNo);

}
