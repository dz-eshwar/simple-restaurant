CREATE DATABASE  IF NOT EXISTS `simplerestaurant` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `simplerestaurant`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: simplerestaurant
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` varchar(45) NOT NULL,
  `menu_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `table_id` int DEFAULT NULL,
  `device_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `order_status` varchar(45) DEFAULT NULL,
  `delete_status` varchar(45) DEFAULT NULL,
  `delete_date` timestamp NULL DEFAULT NULL,
  `order_id` varchar(45) DEFAULT NULL,
  `prep_time` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_details_menu_fk_idx` (`menu_id`),
  KEY `order_details_table_mst_fk_idx` (`table_id`),
  KEY `order_details_device_fk_idx` (`device_id`),
  KEY `order_details_user_fk_idx` (`user_id`),
  KEY `order_details_order_fk_idx` (`order_id`),
  CONSTRAINT `order_details_device_fk` FOREIGN KEY (`device_id`) REFERENCES `device_mst` (`id`),
  CONSTRAINT `order_details_menu_fk` FOREIGN KEY (`menu_id`) REFERENCES `menu_mst` (`id`),
  CONSTRAINT `order_details_order_fk` FOREIGN KEY (`order_id`) REFERENCES `order_trans` (`order_id`),
  CONSTRAINT `order_details_table_mst_fk` FOREIGN KEY (`table_id`) REFERENCES `table_mst` (`id`),
  CONSTRAINT `order_details_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES ('10',2,2,1,2,2,'2022-06-16 02:30:19','ORDER_CONFIRM',NULL,NULL,'1272784105',NULL),('11',5,5,1,2,2,'2022-06-16 02:30:19','ORDER_CONFIRM',NULL,NULL,'1272784105',NULL),('12',2,4,2,2,2,'2022-06-16 02:41:36','ORDER_CONFIRM',NULL,NULL,'-563879511',NULL),('13',6,3,1,2,2,'2022-06-16 02:41:36','ORDER_CONFIRM',NULL,NULL,'-563879511',NULL),('14',2,2,2,2,2,'2022-06-16 02:45:56','ORDER_CONFIRM',NULL,NULL,'33e70946-3510-4092-bcb5-47a1d0dd9be6',NULL),('15',6,3,1,2,2,'2022-06-16 02:45:56','ORDER_CONFIRM',NULL,NULL,'33e70946-3510-4092-bcb5-47a1d0dd9be6',NULL),('17',8,3,1,2,2,'2022-06-16 03:47:23','ORDER_CONFIRM',NULL,NULL,'9ff39dbe-e3fb-405a-b3bf-31a5510f9e35',NULL),('18',3,3,2,2,2,'2022-06-16 03:52:53','ORDER_CONFIRM',NULL,NULL,'851a57d8-f566-43b3-963a-a30977fccbb4',NULL),('19',8,3,1,2,2,'2022-06-16 03:52:54','ORDER_CONFIRM',NULL,NULL,'851a57d8-f566-43b3-963a-a30977fccbb4',NULL),('20',3,1,2,2,2,'2022-06-16 03:55:19','ORDER_CONFIRM',NULL,NULL,'315ceb90-22fc-430c-957a-d880203c88e1',NULL),('22',3,3,2,2,2,'2022-06-18 15:11:31','ORDER_CONFIRM',NULL,NULL,'9ab91e6b-6118-47b6-8733-21bfb80b9e20',NULL),('23',8,3,1,2,2,'2022-06-18 15:11:31','ORDER_CONFIRM',NULL,NULL,'9ab91e6b-6118-47b6-8733-21bfb80b9e20',NULL),('6',1,2,1,2,2,'2022-06-16 23:15:33','waiting confirmation',NULL,NULL,NULL,NULL),('7',5,1,1,2,2,'2022-06-16 23:15:33','waiting confirmation',NULL,NULL,NULL,NULL),('8',1,2,1,2,2,'2022-06-16 23:21:52','waiting confirmation',NULL,NULL,NULL,NULL),('9',5,4,1,2,2,'2022-06-16 23:21:52','waiting confirmation',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-19  0:33:05
