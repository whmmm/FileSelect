/*  使用 sqlyog 导入 mysql 版本 8.0
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 8.0.17 : Database - resource_manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`resource_manager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `resource_manager`;

/*Table structure for table `res_file` */

DROP TABLE IF EXISTS `res_file`;

CREATE TABLE `res_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(50) NOT NULL COMMENT '文件名称',
  `path` varchar(300) NOT NULL COMMENT '全路径',
  `dir_path` varchar(255) NOT NULL COMMENT '目录路径',
  `type` tinyint(1) NOT NULL COMMENT '文件类型\n1. 图片\n2. doc 文档\n3. 视频\n4. 音频',
  `md5` char(32) NOT NULL COMMENT 'md5 标识 ',
  `user_key` varchar(50) DEFAULT NULL COMMENT '上传标识',
  `upload_time` timestamp NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`),
  KEY `res_file_file_name_index` (`file_name` DESC),
  KEY `res_file_md5_index` (`md5` DESC),
  KEY `res_file_upload_time_index` (`upload_time` DESC),
  KEY `res_file_user_key_index` (`user_key` DESC)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `res_file` */

insert  into `res_file`(`id`,`file_name`,`path`,`dir_path`,`type`,`md5`,`user_key`,`upload_time`) values 
(1,'TIM图片20190620151038.jpg','/upload/user_test/TIM图片20190620151038.jpg','E:/upload-resource/upload/user_test',1,'f3d3984027789c2742717b69d0658197','user_test',NULL),
(2,'TIM截图20190614124632.png','/upload/user_test/TIM截图20190614124632.png','E:/upload-resource/upload/user_test',1,'ce25072269a442739b2acf20fc50234d','user_test',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
