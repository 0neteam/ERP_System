package com.oneteam.domain.dept;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name="dept_user")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptUserEntity extends BaseEntity {

    @Column(name = "deptNo", nullable = false)
    private Long deptNo;

    @Column(name = "userNo", nullable = false)
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", insertable=false, updatable = false)
    private UserEntity targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
    private UserEntity regUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
    private UserEntity modUser;

}
