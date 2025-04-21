package com.oneteam.domain.user;

import com.oneteam.domain.BaseEntity;
import com.oneteam.domain.dept.DeptEntity;
import com.oneteam.domain.dept.DeptUserEntity;
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

    @Column(name = "licence1", nullable = true)
    private char licence1;

    @Column(name = "licence2", nullable = true)
    private char licence2;

    @Column(name = "licence3", nullable = true)
    private char licence3;

    @Column(name = "licence4", nullable = true)
    private char licence4;

    @OrderBy("no asc")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dept_user",
            joinColumns = @JoinColumn(name = "userNo"),
            inverseJoinColumns = @JoinColumn(name = "deptNo"))
    private Set<DeptEntity> depts = new HashSet<>();

    @OneToMany(mappedBy = "targetUser")
    private Set<DeptUserEntity> deptUsers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regUserNo", insertable=false, updatable = false)
    private UserEntity regUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modUserNo", insertable=false, updatable = false)
    private UserEntity modUser;

}
