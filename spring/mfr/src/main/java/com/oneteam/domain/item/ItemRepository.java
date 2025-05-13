package com.oneteam.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

  @Query(
      value = "SELECT * FROM item i WHERE i.useYn = :useYn AND CAST(i.no AS CHAR) LIKE CONCAT('%', :no, '%') AND i.name LIKE CONCAT('%', :name, '%')",
      countQuery = "",
      nativeQuery = true)
  public Page<ItemEntity> findAllByUseYn(@Param("useYn") char useYn, @Param("no") String no, @Param("name") String name, Pageable pageable);
  public Optional<ItemEntity> findByNoAndUseYn(Long no, char useYn);

}