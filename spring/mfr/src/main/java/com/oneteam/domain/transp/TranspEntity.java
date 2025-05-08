package com.oneteam.domain.transp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import com.oneteam.domain.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transp")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranspEntity extends BaseEntity {

  @Column(name = "userNo", nullable = false, insertable = false, updatable = false)
  private Long userNo;

  @Column(name = "vehicleNo", nullable = false, insertable = false, updatable = false)
  private Long vehicleNo;

  @Column(name = "depDate", nullable = true, updatable = false, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime depDate;

  @Column(name = "arrDate", nullable = true, updatable = false, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime arrDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "userNo", insertable=false, updatable = false)
  private UserEntity user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "vehicleNo", insertable=false, updatable = false)
  private VehicleEntity vehicle;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
