-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: Course_Selection_System
-- ------------------------------------------------------
-- Server version	5.7.18-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Course`
--

  private Long id;
    private String courseNumber;
    private String courseName;
    private String property;
    private String className;
    private int stuTotal;
    private int classHour;
    private int shiyanHour;
    private String note;
    private String institutionNumber;
    private String institutionName;

DROP TABLE IF EXISTS `Course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseNumber` varchar(200) NOT NULL,
  `courseName` varchar(200) NOT NULL,
  `property` varchar(200) NOT NULL,
  `className` varchar(200) NOT NULL,
  `stuTotal` int(11) NOT NULL,
  `classHour` int(11) NOT NULL,
  `shiyanHour` int(11) NOT NULL,
  `note` varchar(200) NOT NULL,
  `institutionNumber` varchar(200) NOT NULL,
  `institutionName` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  unique  key (`courseNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Course`
--

LOCK TABLES `Course` WRITE;
/*!40000 ALTER TABLE `Course` DISABLE KEYS */;
INSERT INTO `Course` VALUES
(1,'1001','c语言程序设计','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(2,'1002','计算机网络','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(3,'1003','c语言程序设计','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(4,'1004','java程序设计','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(5,'1005','大学英语','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(6,'1006','高等数学','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(7,'1007','计算机组成','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(8,'1008','数据结构','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系'),
(9,'1009','日语','必修','计算机1班', 15,40,15, '备注1', '1001','计算机系');
/*!40000 ALTER TABLE `Course` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-19 16:59:26
