package com.oneteam.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no", nullable = false)
  private Long no;

  @Column(name = "useYn", nullable = false, length = 1)
  private char useYn;

  @CreationTimestamp
  @Column(name = "regDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @Column(name = "regUserNo", nullable = true)
  private Long regUserNo;

  @UpdateTimestamp
  @Column(name = "modDate", nullable = true, insertable=false, columnDefinition = "TIMESTAMP")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  @Column(name = "modUserNo", nullable = true, insertable=false)
  private Long modUserNo;

}