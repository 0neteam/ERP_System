package com.oneteam.domain.user;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.dept.DeptEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

  @Column(name = "email", nullable = false, length = 100, unique = true)
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "fileNo", nullable = true)
  private Long fileNo;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "dept_user",
      joinColumns = @JoinColumn(name = "userNo"),
      inverseJoinColumns = @JoinColumn(name = "deptNo"))
  private Set<DeptEntity> depts = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
