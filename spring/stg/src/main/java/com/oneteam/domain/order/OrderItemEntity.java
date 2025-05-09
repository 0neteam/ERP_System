package com.oneteam.domain.order;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.item.ItemEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_item")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity extends BaseEntity {

  @Column(name = "orderNo", nullable = false)
  private Long orderNo;

  @Column(name = "itemNo", nullable = false)
  private Long itemNo;

  @Column(name = "qty", nullable = false)
  private int qty;

  @Column(name = "price", nullable = false)
  private int price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderNo", insertable = false, updatable = false)
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "itemNo", insertable = false, updatable = false)
  private ItemEntity item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
