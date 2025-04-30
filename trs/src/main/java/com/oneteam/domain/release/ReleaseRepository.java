package com.oneteam.domain.release;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {

  public Page<ReleaseEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<ReleaseEntity> findByNoAndUseYn(Long no, char useYn);

  public Page<ReleaseEntity> findAllByTranspNoAndUseYn(Long transpNo, char useYn, Pageable pageable);

  @Query("SELECT r from ReleaseEntity r JOIN r.orderItem oi WHERE oi.orderNo = :no AND r.transpNo is null AND r.useYn = :useYn")
  public List<ReleaseEntity> findAllByOrderItemNoAndUseYnAndTranspNoNull(@Param("no") Long no, @Param("useYn") char useYn);

  @Query("SELECT r FROM ReleaseEntity r WHERE r.orderItemNo IN (SELECT no FROM OrderItemEntity oi WHERE oi.orderNo = :no) AND transpNo IS null AND useYn = :useYn")
  public List<ReleaseEntity> findAllByOrderNoAndTranspIsNull(@Param("no") int no, @Param("useYn") char useYn);

}
