package com.oneteam.domain.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.item.ItemEntity;
import com.oneteam.domain.order.OrderItemEntity;
import com.oneteam.domain.transp.TranspEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="releases")
@Setter
@Getter
//@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseEntity extends BaseEntity {

  @Column(name = "orderItemNo", nullable = false)
  private Long orderItemNo;

  @Column(name = "itemNo", nullable = false)
  private Long itemNo;

  @Column(name = "transpNo", nullable = true)
  private Long transpNo;

  @Column(name = "oQty", nullable = false)
  private int oQty;

  @Column(name = "iQty", nullable = true)
  private int iQty;

  @Column(name = "pQty", nullable = true)
  private int pQty;

  @Column(name = "status", nullable = false)
  private int status;

  @Column(name = "depDate", nullable = true, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime depDate;

  @Column(name = "arrDate", nullable = true, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime arrDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "itemNo", insertable = false, updatable = false)
  private ItemEntity item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderItemNo", insertable = false, updatable = false)
  private OrderItemEntity orderItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transpNo", insertable = false, updatable = false)
  private TranspEntity transp;

}
