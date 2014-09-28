/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.27 : Database - ecommerce
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_brand` */

CREATE TABLE `t_brand` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `NAME` varchar(50) COLLATE utf8_bin NOT NULL,
  `DESC` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `WEBSITE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `FAX` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'A-启用，B-下架，D-已经删除',
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LOGO` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_cart_info` */

CREATE TABLE `t_cart_info` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `CART_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_1` (`CART_ID`),
  KEY `FK_Reference_2` (`PRODUCT_ID`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`CART_ID`) REFERENCES `t_shopping_cart` (`ID`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `t_product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_category` */

CREATE TABLE `t_category` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `DESC` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `ORDER` int(11) DEFAULT NULL,
  `COVER` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LEVEL` int(1) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_7` (`PARENT_ID`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`PARENT_ID`) REFERENCES `t_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_order` */

CREATE TABLE `t_order` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `SN` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `ADDRESS` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `LEAVE_MESSAGE` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `ADDRESSEE_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'A-用户已生成未发货，B-系统已发货，C-用户已确认收货，D-用户已付款，订单完成，E-用户已退货，订单取消',
  `TOTAL_PRICE` double DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `SEND_TIME` datetime DEFAULT NULL,
  `DELIVERY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_5` (`USER_ID`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_prod_enshrine_log` */

CREATE TABLE `t_prod_enshrine_log` (
  `ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  KEY `FK_Reference_8` (`ID`),
  KEY `FK_t_prod_enshrine_log_p` (`PRODUCT_ID`),
  KEY `FK_t_prod_enshrine_log_u` (`USER_ID`),
  CONSTRAINT `FK_t_prod_enshrine_log_p` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `t_product` (`ID`),
  CONSTRAINT `FK_t_prod_enshrine_log_u` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_product` */

CREATE TABLE `t_product` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `BRAND_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `BRAND_NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_bin NOT NULL,
  `PRODUCT_IMG1` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_IMG2` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_IMG3` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_IMG4` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_IMG5` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_URL_IMG1` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_URL_IMG2` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_URL_IMG3` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_URL_IMG4` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `PRODUCT_URL_IMG5` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `DESC` text COLLATE utf8_bin,
  `MODEL_NO` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `YEAR_MADE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `SERIAL_NO` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `UNIT_PRICE` double(10,2) DEFAULT NULL,
  `SELL_PRICE` double(10,2) DEFAULT NULL,
  `IS_DISCOUNT` int(11) DEFAULT '0',
  `STOCK` int(11) DEFAULT NULL,
  `SKU` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `APPROVED_BY` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `APPROVED_TIME` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_TIME` timestamp NULL DEFAULT NULL,
  `UPDATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_TIME` timestamp NULL DEFAULT NULL,
  `TOKEN_OFF_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `TOKEN_OFF_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_4` (`CATEGORY_ID`),
  KEY `FK_t_product_b` (`BRAND_ID`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `t_category` (`ID`),
  CONSTRAINT `FK_t_product_b` FOREIGN KEY (`BRAND_ID`) REFERENCES `t_brand` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_product_hot` */

CREATE TABLE `t_product_hot` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `PRODUCT_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `ORDER` int(11) DEFAULT NULL,
  `TYPE` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'A-主页列表中热卖产品，B-主页幻灯片中展示产品',
  `PROD_IMG` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'type为B 时使用',
  `CREATED_TIME` datetime DEFAULT NULL,
  `CREATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_14` (`PRODUCT_ID`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `t_product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_product_inst` */

CREATE TABLE `t_product_inst` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `PRODUCT_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `ORDER_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL,
  `DESC` text COLLATE utf8_bin,
  `UNIT_PRICE` double DEFAULT NULL,
  `SELL_PRICE` double DEFAULT NULL,
  `PROD_COVER` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_10` (`ORDER_ID`),
  KEY `FK_Reference_11` (`PRODUCT_ID`),
  KEY `FK_Reference_12` (`USER_ID`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`ORDER_ID`) REFERENCES `t_order` (`ID`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `t_product` (`ID`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_shopping_cart` */

CREATE TABLE `t_shopping_cart` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `USER_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` char(1) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_13` (`USER_ID`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_sys_hat_area` */

CREATE TABLE `t_sys_hat_area` (
  `ID` int(11) NOT NULL,
  `_CODE` char(6) COLLATE utf8_bin DEFAULT NULL,
  `_NAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `_CITY_CODE` char(6) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_sys_hat_city` */

CREATE TABLE `t_sys_hat_city` (
  `ID` int(11) NOT NULL,
  `_CODE` char(6) COLLATE utf8_bin DEFAULT NULL,
  `_NAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `_PROVINCE_CODE` char(6) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_sys_hat_province` */

CREATE TABLE `t_sys_hat_province` (
  `ID` int(11) NOT NULL,
  `_CODE` char(6) COLLATE utf8_bin DEFAULT NULL,
  `_NAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_user` */

CREATE TABLE `t_user` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `STATUS` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'A-未激活，B-已经激活，D-已删除',
  `NAME` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(50) COLLATE utf8_bin NOT NULL,
  `CELL_PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `USER_NAME` varchar(50) COLLATE utf8_bin NOT NULL,
  `PASSWORD` varchar(50) COLLATE utf8_bin NOT NULL,
  `LAST_ACCESS_IP` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATED_BY` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USER_ADMIN` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_user_address` */

CREATE TABLE `t_user_address` (
  `ID` char(32) COLLATE utf8_bin NOT NULL,
  `USER_ID` char(32) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PROVENCE` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `CITY` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `AREA` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `STREET` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `CELL_PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_6` (`USER_ID`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`USER_ID`) REFERENCES `t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
