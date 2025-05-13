package com.oneteam.domain.order;

import com.oneteam.dto.OrderItemsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

  public Page<OrderItemEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Page<OrderItemEntity> findAllByUseYnAndNo(char useYn, Long no, Pageable pageable);
  public Optional<OrderItemEntity> findByNoAndUseYn(Long no, char useYn);
  public List<OrderItemEntity> findByOrderNoAndUseYn(Long orderNo, char useYn);
  public List<OrderItemEntity> findByOrderNo(Long orderNo);

  @Query(value = "SELECT o.`no`, o.`itemNo`, i.`bundle`, o.`qty`, IFNULL(r.oQty, 0) AS oQty, IFNULL(r.iQty, 0) AS iQty, IFNULL(r.pQty, 0) AS pQty FROM order_item AS o LEFT OUTER JOIN item AS i ON (o.itemNo = i.`no` AND i.useYn = 'Y') LEFT OUTER JOIN (SELECT `orderItemNo`, `itemNo`, SUM(`oQty`) AS oQty, SUM(`iQty`) AS iQty, SUM(`pQty`) AS pQty FROM releases WHERE useYn = 'Y' GROUP BY `orderItemNo`, `itemNo`) AS r ON (o.`no` = r.orderItemNo AND o.itemNo = r.itemNo) WHERE o.useYn = :useYn AND o.`orderNo` = :no",
      countQuery = "SELECT count(*) FROM order_item AS o LEFT OUTER JOIN item AS i ON (o.itemNo = i.`no` AND i.useYn = 'Y') LEFT OUTER JOIN (SELECT `orderItemNo`, `itemNo`, SUM(`oQty`) AS oQty, SUM(`iQty`) AS iQty, SUM(`pQty`) AS pQty FROM releases WHERE useYn = 'Y' GROUP BY `orderItemNo`, `itemNo`) AS r ON (o.`no` = r.orderItemNo AND o.itemNo = r.itemNo) WHERE o.useYn = :useYn AND o.`orderNo` = :no",
      nativeQuery = true)
  public Page<OrderItemsDTO> findAllByUseYnAndItem(@Param("useYn") char useYn, @Param("no") Long no, Pageable pageable);

}
