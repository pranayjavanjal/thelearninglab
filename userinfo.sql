/*
SQLyog Enterprise Trial - MySQL GUI v7.11 
MySQL - 8.0.20 : Database - lms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`lms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `tll`;

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `department` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `employee_id` int DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `joining_date` datetime DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`active`,`confirm_date`,`department`,`email`,`employee_id`,`first_name`,`joining_date`,`last_name`,`password`,`role`) values (1,'','2020-05-27 00:00:00','Develpment','pranay@yopmail.com',1111,'Pranay','2020-05-27 00:00:00','Javanjal','$2a$10$N/3zuHAK.yeGgWeU6dUtCOsC2T3lYIm3KotlyWL8QDdvQPjm23vgW','ADMIN'),(2,'','2020-05-27 00:00:00','Management','parag@yopmail.com',0,'Parag','2020-05-27 00:00:00','JAvanjal','$2a$10$ywJptRrnymRyhEeppCIa2utaB7JoWDvzGQBMv49zvHnHnVvOW5DY6','MANAGER');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
