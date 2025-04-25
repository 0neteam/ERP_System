package com.oneteam.domain.order;

import com.oneteam.dto.OrderCntDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

  public Page<OrderItemEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Optional<OrderItemEntity> findByNoAndUseYn(Long no, char useYn);
  public List<OrderItemEntity> findByOrderNoAndUseYn(Long orderNo, char useYn);
  public List<OrderItemEntity> findByOrderNo(Long orderNo);

  @Query(value = "SELECT o.`orderNo`, sum(o.`qty`) - sum(IFNULL(r.iQty, 0)) AS cnt FROM order_item AS o LEFT OUTER JOIN (SELECT r.`orderItemNo`, SUM(IFNULL(`iQty`, 0)) AS iQty FROM releases AS r WHERE useYn = 'Y' AND EXISTS (SELECT 1 FROM order_item WHERE orderNo = :orderNo1 AND r.orderItemNo = `no`) GROUP BY `orderItemNo`) AS r ON (o.`no` = r.orderItemNo) WHERE o.useYn = 'Y' AND o.`orderNo` = :orderNo2 GROUP BY o.`orderNo`",
      nativeQuery = true)
  public Optional<OrderCntDTO> findByOrderCnt(@Param("orderNo1") Long orderNo1, @Param("orderNo2") Long orderNo2);

}
