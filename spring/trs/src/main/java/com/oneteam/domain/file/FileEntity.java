package com.oneteam.domain.file;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="file")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity extends BaseEntity {

  @Column(name = "origin", nullable = false, length = 100)
  private String origin;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "attachPath", nullable = false, length = 100)
  private String attachPath;

  @Column(name = "ext", nullable = false, length = 10)
  private String ext;

  @Column(name = "mediaType", nullable = false, length = 255)
  private String mediaType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
  private UserEntity regUser;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
  private UserEntity modUser;

}
