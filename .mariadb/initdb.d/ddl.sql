-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        11.6.2-MariaDB-ubu2404 - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- edu 데이터베이스 구조 내보내기
DROP DATABASE IF EXISTS `edu`;
CREATE DATABASE IF NOT EXISTS `edu` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `edu`;

-- 테이블 edu.dept 구조 내보내기
DROP TABLE IF EXISTS `dept`;
CREATE TABLE IF NOT EXISTS `dept` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '부서번호',
  `name` varchar(100) NOT NULL COMMENT '부서코드',
  `deptName` varchar(100) NOT NULL DEFAULT '임시' COMMENT '부서명',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  UNIQUE KEY `UNIQUE_name` (`name`) USING BTREE,
  KEY `FK_dept_reg_user` (`regUserNo`),
  KEY `FK_dept_mod_user` (`modUserNo`),
  CONSTRAINT `FK_dept_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_dept_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='부서';

-- 테이블 데이터 edu.dept:~5 rows (대략적) 내보내기
DELETE FROM `dept`;
INSERT INTO `dept` (`no`, `name`, `deptName`, `useYn`, `regDate`, `regUserNo`, `modDate`, `modUserNo`) VALUES
	(1, 'ADMIN', '관리자', 'Y', '2025-04-01 17:30:38', 1, NULL, NULL),
	(2, 'STG', '창고', 'Y', '2025-04-01 17:34:47', 1, NULL, NULL),
	(3, 'MFR', '제조', 'Y', '2025-04-01 18:31:39', 1, NULL, NULL),
	(4, 'TRS', '운송', 'Y', '2025-04-01 18:32:00', 1, '2025-04-14 15:52:04', 1),
	(5, 'DRI', '기사', 'Y', '2025-04-01 18:33:32', 1, NULL, NULL);

-- 테이블 edu.dept_user 구조 내보내기
DROP TABLE IF EXISTS `dept_user`;
CREATE TABLE IF NOT EXISTS `dept_user` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '부서별 사용자번호',
  `deptNo` bigint(20) NOT NULL COMMENT '부서번호',
  `userNo` bigint(20) NOT NULL COMMENT '사용자번호',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`) USING BTREE,
  KEY `FK_dept_user_dept` (`deptNo`),
  KEY `FK_dept_user_user` (`userNo`),
  KEY `FK_dept_user_reg_user` (`regUserNo`),
  KEY `FK_dept_user_mod_user` (`modUserNo`),
  CONSTRAINT `FK_dept_user_dept` FOREIGN KEY (`deptNo`) REFERENCES `dept` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_dept_user_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_dept_user_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_dept_user_user` FOREIGN KEY (`userNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='부서별 사용자';

-- 테이블 데이터 edu.dept_user:~2 rows (대략적) 내보내기
DELETE FROM `dept_user`;
INSERT INTO `dept_user` (`no`, `deptNo`, `userNo`, `useYn`, `regDate`, `regUserNo`, `modDate`, `modUserNo`) VALUES
	(1, 1, 1, 'Y', '2025-04-01 17:30:57', 1, NULL, NULL),
	(2, 2, 2, 'Y', '2025-04-01 17:34:59', 1, NULL, NULL);

-- 테이블 edu.file 구조 내보내기
DROP TABLE IF EXISTS `file`;
CREATE TABLE IF NOT EXISTS `file` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '파일번호',
  `origin` varchar(100) NOT NULL COMMENT '첨부 시의 실제 파일명',
  `name` varchar(100) NOT NULL COMMENT '저장 파일명',
  `attachPath` varchar(100) NOT NULL COMMENT '저장 경로',
  `ext` varchar(10) NOT NULL COMMENT '확장자',
  `mediaType` varchar(255) NOT NULL COMMENT '미디어타입',
  `useYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_file_reg_user` (`regUserNo`),
  KEY `FK_file_mod_user` (`modUserNo`),
  CONSTRAINT `FK_file_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_file_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='파일';

-- 테이블 데이터 edu.file:~1 rows (대략적) 내보내기
DELETE FROM `file`;
INSERT INTO `file` (`no`, `origin`, `name`, `attachPath`, `ext`, `mediaType`, `useYN`, `regDate`, `regUserNo`, `modDate`, `modUserNo`) VALUES
	(1, '테스트.png', '469819563980800', '/usr/local/upload/20250402/1', '.png', 'image/png', 'Y', '2025-04-02 10:45:01', 1, NULL, NULL);

-- 테이블 edu.item 구조 내보내기
DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '품목번호',
  `name` varchar(100) NOT NULL COMMENT '품목명',
  `bundle` int(11) NOT NULL DEFAULT 1 COMMENT '묶음 단위',
  `price` int(11) DEFAULT 0 COMMENT '묶음 단가',
  `fileNo` bigint(20) DEFAULT NULL COMMENT '파일번호',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_item_file` (`fileNo`),
  KEY `FK_item_reg_user` (`regUserNo`),
  KEY `FK_item_mod_user` (`modUserNo`),
  CONSTRAINT `FK_item_file` FOREIGN KEY (`fileNo`) REFERENCES `file` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='품목';

-- 테이블 데이터 edu.item:~0 rows (대략적) 내보내기
DELETE FROM `item`;

-- 테이블 edu.orders 구조 내보내기
DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '발주번호',
  `reqDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '발주 요청일',
  `perDate` datetime DEFAULT NULL COMMENT '발주 승인일',
  `cancelDate` datetime DEFAULT NULL COMMENT '발주 취소일',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '상태',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_order_reg_user` (`regUserNo`),
  KEY `FK_order_mod_user` (`modUserNo`),
  CONSTRAINT `FK_order_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_order_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='발주';

-- 테이블 데이터 edu.orders:~0 rows (대략적) 내보내기
DELETE FROM `orders`;

-- 테이블 edu.order_item 구조 내보내기
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE IF NOT EXISTS `order_item` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '발주요청 품목번호',
  `orderNo` bigint(20) NOT NULL COMMENT '발주번호',
  `itemNo` bigint(20) NOT NULL COMMENT '품목번호',
  `qty` int(11) NOT NULL DEFAULT 0 COMMENT '수량',
  `price` int(11) DEFAULT NULL COMMENT '단가',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_order_item_order` (`orderNo`),
  KEY `FK_order_item_item` (`itemNo`),
  KEY `FK_order_item_reg_user` (`regUserNo`),
  KEY `FK_order_item_mod_user` (`modUserNo`),
  CONSTRAINT `FK_order_item_item` FOREIGN KEY (`itemNo`) REFERENCES `item` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_order_item_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_order_item_order` FOREIGN KEY (`orderNo`) REFERENCES `orders` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_order_item_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='발주 품목';

-- 테이블 데이터 edu.order_item:~0 rows (대략적) 내보내기
DELETE FROM `order_item`;

-- 테이블 edu.releases 구조 내보내기
DROP TABLE IF EXISTS `releases`;
CREATE TABLE IF NOT EXISTS `releases` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '입출고번호',
  `orderItemNo` bigint(20) NOT NULL COMMENT '발주요청 품목번호',
  `itemNo` bigint(20) NOT NULL COMMENT '품목번호',
  `transpNo` bigint(20) DEFAULT NULL COMMENT '운송번호',
  `oQty` int(11) NOT NULL DEFAULT 0 COMMENT '출고수량',
  `iQty` int(11) DEFAULT 0 COMMENT '입고수량',
  `pQty` int(11) DEFAULT 0 COMMENT '불량수량',
  `depDate` datetime DEFAULT NULL COMMENT '출발일',
  `arrDate` datetime DEFAULT NULL COMMENT '도착일',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '입출고상태값 (0: 대기, 1: 정상, 2: 비정상)',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_release_order_item` (`orderItemNo`),
  KEY `FK_release_transp` (`transpNo`),
  KEY `FK_release_reg_user` (`regUserNo`),
  KEY `FK_release_mod_user` (`modUserNo`),
  KEY `FK_release_item` (`itemNo`),
  CONSTRAINT `FK_release_item` FOREIGN KEY (`itemNo`) REFERENCES `item` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_release_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_release_order_item` FOREIGN KEY (`orderItemNo`) REFERENCES `order_item` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_release_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_release_transp` FOREIGN KEY (`transpNo`) REFERENCES `transp` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='입출고';

-- 테이블 데이터 edu.releases:~0 rows (대략적) 내보내기
DELETE FROM `releases`;

-- 테이블 edu.stock 구조 내보내기
DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '재고번호',
  `itemNo` bigint(20) NOT NULL COMMENT '품목번호',
  `qty` int(11) NOT NULL DEFAULT 0 COMMENT '수량',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_stock_item` (`itemNo`),
  KEY `FK_stock_reg_user` (`regUserNo`),
  KEY `FK_stock_mod_user` (`modUserNo`),
  CONSTRAINT `FK_stock_item` FOREIGN KEY (`itemNo`) REFERENCES `item` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_stock_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_stock_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='재고';

-- 테이블 데이터 edu.stock:~0 rows (대략적) 내보내기
DELETE FROM `stock`;

-- 테이블 edu.transp 구조 내보내기
DROP TABLE IF EXISTS `transp`;
CREATE TABLE IF NOT EXISTS `transp` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '운송번호',
  `userNo` bigint(20) NOT NULL COMMENT '운송자번호',
  `vehicleNo` bigint(20) NOT NULL COMMENT '운송차량번호',
  `depDate` datetime DEFAULT NULL COMMENT '출발일',
  `arrDate` datetime DEFAULT NULL COMMENT '도착일',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자번호',
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  KEY `FK_transp_user` (`userNo`),
  KEY `FK_transp_reg_user` (`regUserNo`),
  KEY `FK_transp_mod_user` (`modUserNo`),
  KEY `FK_transp_vehicle` (`vehicleNo`) USING BTREE,
  CONSTRAINT `FK_transp_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_transp_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_transp_user` FOREIGN KEY (`userNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_transp_vehicle` FOREIGN KEY (`vehicleNo`) REFERENCES `vehicle` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='운송';

-- 테이블 데이터 edu.transp:~0 rows (대략적) 내보내기
DELETE FROM `transp`;

-- 테이블 edu.user 구조 내보내기
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '사용자번호',
  `email` varchar(100) NOT NULL COMMENT '이메일',
  `password` varchar(100) DEFAULT NULL COMMENT '비밀번호',
  `name` varchar(100) NOT NULL COMMENT '이름',
  `fileNo` bigint(20) DEFAULT NULL COMMENT '파일번호',
  `licence1` char(1) DEFAULT 'N' COMMENT '1종특수',
  `licence2` char(1) DEFAULT 'N' COMMENT '1종대형',
  `licence3` char(1) DEFAULT 'N' COMMENT '1종보통',
  `licence4` char(1) DEFAULT 'N' COMMENT '2종보통',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일',
  `regUserNo` bigint(20) DEFAULT NULL,
  `modDate` datetime DEFAULT NULL COMMENT '수정일',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자번호',
  PRIMARY KEY (`no`),
  UNIQUE KEY `UNIQUE_email` (`email`) USING BTREE,
  KEY `FK_user_file` (`fileNo`),
  CONSTRAINT `FK_user_file` FOREIGN KEY (`fileNo`) REFERENCES `file` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='사용자';

-- 테이블 데이터 edu.user:~3 rows (대략적) 내보내기
DELETE FROM `user`;
INSERT INTO `user` (`no`, `email`, `password`, `name`, `fileNo`, `licence1`, `licence2`, `licence3`, `licence4`, `useYn`, `regDate`, `regUserNo`, `modDate`, `modUserNo`) VALUES
	(1, 'test@test', '$2a$10$tP7bmermcHRLp/PWT1LereVPn4qmJinID3dPL79NPH7R4I2K/ijf6', '테스트', 1, 'N', 'N', 'N', 'N', 'Y', '2025-04-01 17:25:41', 1, '2025-04-14 10:29:09', NULL),
	(2, 'img@img', '$2a$10$BGF6/cidiI3xrX.GuANjEO3HPUfvnOBfbX0y8bh4BmLmDf7Y63hWu', '임진', NULL, 'N', 'N', 'N', 'N', 'Y', '2025-04-02 16:47:26', 2, '2025-04-02 16:47:26', NULL),
	(3, 'admin@admin', '$2a$10$QSQYzDTh9FFn7moaJzlaU.eWRf5ATinmP44uvW0cQCF5LuWqvvMEK', '어드민', NULL, 'N', 'N', 'N', 'N', 'Y', '2025-04-03 09:18:11', 3, '2025-04-22 23:38:03', 3);

-- 테이블 edu.vehicle 구조 내보내기
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '차량번호',
  `regNumber` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci NOT NULL COMMENT '차량등록번호',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '차종 (1: 소형화물, 2: 카고, 3: 탑차, 4: 윙바디, 5: 트레일러)',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci NOT NULL COMMENT '차량명',
  `licence` int(11) NOT NULL DEFAULT 0 COMMENT '필요 면허 (1: 1종 특수, 2: 1종 대형, 3: 1종 보통, 4: 2종 보통)',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '상태 (1:대기, 2: 운행, 3:점검, 4: 폐차)',
  `fileNo` bigint(20) DEFAULT NULL COMMENT '파일번호-자동차등록증 ',
  `useYn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `regDate` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일자',
  `regUserNo` bigint(20) NOT NULL COMMENT '등록자 번호',
  `modDate` datetime DEFAULT current_timestamp() COMMENT '수정일자',
  `modUserNo` bigint(20) DEFAULT NULL COMMENT '수정자 번호',
  PRIMARY KEY (`no`) USING BTREE,
  UNIQUE KEY `regNumber` (`regNumber`) USING BTREE,
  KEY `regUserNo` (`regUserNo`) USING BTREE,
  KEY `modUserNo` (`modUserNo`) USING BTREE,
  KEY `fileNo` (`fileNo`) USING BTREE,
  CONSTRAINT `FK_vehicle_file` FOREIGN KEY (`fileNo`) REFERENCES `file` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_vehicle_mod_user` FOREIGN KEY (`modUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_vehicle_reg_user` FOREIGN KEY (`regUserNo`) REFERENCES `user` (`no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 edu.vehicle:~0 rows (대략적) 내보내기
DELETE FROM `vehicle`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

commit;