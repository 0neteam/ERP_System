package com.oneteam.domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseEntity {

  @CreationTimestamp
  @Column(name = "reqDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime reqDate;

  @Column(name = "perDate", nullable = true, updatable = true, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime perDate;

  @Column(name = "cancelDate", nullable = true, updatable = true, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime cancelDate;

  @Column(name = "status", nullable = false)
  private int status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
