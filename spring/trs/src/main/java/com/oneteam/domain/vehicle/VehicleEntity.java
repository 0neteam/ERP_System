package com.oneteam.domain.vehicle;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="vehicle")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity extends BaseEntity {

  @Column(name = "regNumber", nullable = false, length = 8)
  private String regNumber;

  @Column(name = "type", nullable = false)
  private int type;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "licence", nullable = false)
  private int licence;

  @Column(name = "status", nullable = false)
  private int status;

  @Column(name = "fileNo", nullable = true)
  private Long fileNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
