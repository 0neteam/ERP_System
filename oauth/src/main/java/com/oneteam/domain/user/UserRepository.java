package com.oneteam.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Page<UserEntity> findAllByNameContainingAndUseYn(String name, char useYn, Pageable pageable);

  Optional<UserEntity> findByEmailAndUseYn(String email, char useYn);
  UserEntity findByEmail(String email);

  @Query(value = "SELECT * FROM user WHERE NOT EXISTS (SELECT 1 FROM dept_user WHERE dept_user.userNo = user.`no` AND dept_user.deptNo=:no AND dept_user.useYn = 'Y') AND user.name LIKE CONCAT('%', :name , '%')",
      countQuery = "SELECT COUNT(*) FROM user WHERE NOT EXISTS (SELECT 1 FROM dept_user WHERE dept_user.userNo = user.`no` AND dept_user.deptNo=:no AND dept_user.useYn = 'Y') AND user.name LIKE CONCAT('%', :name , '%')",
      nativeQuery = true)
  public Page<UserEntity> findByDeptNotUser(@Param("no") Long no, @Param("name") String name, Pageable pageable);

//  @Query(value = "SELECT u.* FROM user AS u LEFT OUTER JOIN dept_user AS du ON (u.`no` = du.userNo AND du.useYn = 'Y') LEFT OUTER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y') WHERE u.useYn = 'Y' AND u.name LIKE CONCAT('%', :name, '%') AND NOT EXISTS (SELECT 1 FROM dept AS dp WHERE dp.`no` = :no AND dp.no = d.no AND dp.useYn = 'Y')",
//      countQuery = "SELECT COUNT(*) FROM user AS u LEFT OUTER JOIN dept_user AS du ON (u.`no` = du.userNo AND du.useYn = 'Y') LEFT OUTER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y') WHERE u.useYn = 'Y' AND u.name LIKE CONCAT('%', :name, '%') AND NOT EXISTS (SELECT 1 FROM dept AS dp WHERE dp.`no` = :no AND dp.no = d.no AND dp.useYn = 'Y')",
//      nativeQuery = true)
//  public Page<UserEntity> findByDeptNotUser(@Param("no") Long no, @Param("name") String name, Pageable pageable);

}
