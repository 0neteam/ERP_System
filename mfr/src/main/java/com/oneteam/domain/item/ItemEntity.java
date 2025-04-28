package com.oneteam.domain.item;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="item")
@Setter
@Getter
//@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity extends BaseEntity {

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "bundle", nullable = false)
  private int bundle;

  @Column(name = "price", nullable = true)
  private int price;

  @Column(name = "fileNo", nullable = true)
  private Long fileNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

  @OneToMany(mappedBy = "item")
  private Set<ReleaseEntity> releases = new HashSet<>();

}
