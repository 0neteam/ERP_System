package com.oneteam.domain.order;

import com.oneteam.dto.TranspUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT u.`no`, u.name, u.email, u.fileNo FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND u.licence1 = 'Y' AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        countQuery = "SELECT count(*) FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND u.licence1 = 'Y' AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        nativeQuery = true)
    public Page<TranspUserDTO> findAllByLicence1(@Param("name") String name, Pageable pageable);
  
    @Query(value = "SELECT u.`no`, u.name, u.email, u.fileNo FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND u.licence2 = 'Y' AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        countQuery = "SELECT count(*) FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND u.licence2 = 'Y' AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        nativeQuery = true)
    public Page<TranspUserDTO> findAllByLicence2(@Param("name") String name, Pageable pageable);
  
    @Query(value = "SELECT u.`no`, u.name, u.email, u.fileNo FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND (u.licence1 = 'Y' OR u.licence2 = 'Y' OR u.licence3 = 'Y') AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        countQuery = "SELECT count(*) FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND (u.licence1 = 'Y' OR u.licence2 = 'Y' OR u.licence3 = 'Y') AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        nativeQuery = true)
    public Page<TranspUserDTO> findAllByLicence3(@Param("name") String name, Pageable pageable);
  
    @Query(value = "SELECT u.`no`, u.name, u.email, u.fileNo FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND (u.licence1 = 'Y' OR u.licence2 = 'Y' OR u.licence3 = 'Y' OR u.licence4 = 'Y') AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        countQuery = "SELECT count(*) FROM user AS u INNER JOIN dept_user AS du ON (du.userNo = u.no AND du.useYn = 'Y') INNER JOIN dept AS d ON (du.deptNo = d.`no` AND d.useYn = 'Y' AND d.name = 'DRI') WHERE u.useYn = 'Y' AND (u.licence1 = 'Y' OR u.licence2 = 'Y' OR u.licence3 = 'Y' OR u.licence4 = 'Y') AND u.name like CONCAT('%', :name, '%') AND u.`no` NOT IN (SELECT DISTINCT userNo FROM transp WHERE useYn = 'Y' AND arrDate IS NULL)",
        nativeQuery = true)
    public Page<TranspUserDTO> findAllByLicence4(@Param("name") String name, Pageable pageable);
  
  }
