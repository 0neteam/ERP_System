package com.oneteam.domain.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

  @Query("SELECT s FROM StockEntity s JOIN FETCH s.item i WHERE s.useYn = :useYn AND CAST(s.itemNo AS string) LIKE %:itemNo% AND i.name LIKE %:name%")
  public Page<StockEntity> findAllByUseYn(@Param("useYn") char useYn, @Param("itemNo") String itemNo, @Param("name") String name, Pageable pageable);
  public Optional<StockEntity> findByNoAndUseYn(Long no, char useYn);

}
