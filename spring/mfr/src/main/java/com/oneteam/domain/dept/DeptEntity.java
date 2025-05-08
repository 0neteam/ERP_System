package com.oneteam.domain.dept;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="dept")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptEntity extends BaseEntity {

  @Column(name = "name", nullable = false, length = 100, unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
