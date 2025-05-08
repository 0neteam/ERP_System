package com.oneteam.domain.stock;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.item.ItemEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="stock")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity extends BaseEntity {

  @Column(name = "itemNo", nullable = false)
  private Long itemNo;

  @Column(name = "qty", nullable = false)
  private int qty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "itemNo", insertable=false, updatable = false)
  private ItemEntity item;

}
