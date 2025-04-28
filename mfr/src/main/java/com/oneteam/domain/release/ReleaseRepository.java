package com.oneteam.domain.release;

import com.oneteam.dto.ReleaseResDTO;
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

  @Query(value = "SELECT DISTINCT o.orderNo, t.`no`, DATE_FORMAT(r.depDate, '%Y-%m-%d') as depDate, DATE_FORMAT(r.arrDate, '%Y-%m-%d') as arrDate FROM transp AS t INNER JOIN releases AS r ON (t.`no` = r.transpNo AND r.useYn = 'Y') INNER JOIN order_item AS o ON (r.orderItemNo = o.`no` AND o.useYn = 'Y') WHERE t.useYn = :useYn AND o.orderNo = :orderNo ",
          nativeQuery = true)
  public Page<ReleaseResDTO> findAllByOrderNoAndUseYn(@Param("orderNo") Long orderNo, @Param("useYn") char useYn, Pageable pageable);

  public List<ReleaseEntity> findAllByTranspNoAndUseYn(Long transpNo, char useYn);
}
