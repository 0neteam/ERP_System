package com.oneteam.domain.dept;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

  @Column(name = "deptName", nullable = false, length = 100, unique = true)
  private String deptName;

  @OrderBy("no asc")
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "dept_user",
      joinColumns = @JoinColumn(name = "deptNo"),
      inverseJoinColumns = @JoinColumn(name = "userNo"))
  private List<UserEntity> users = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
