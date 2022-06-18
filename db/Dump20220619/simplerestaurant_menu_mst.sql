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
-- Table structure for table `menu_mst`
--

DROP TABLE IF EXISTS `menu_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_mst` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) DEFAULT NULL,
  `category_id` int NOT NULL,
  `type_id` int NOT NULL,
  `prep_time` int DEFAULT NULL,
  `createdDateTime` timestamp NULL DEFAULT NULL,
  `price` double DEFAULT NULL,
  `created_date_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `item_name_UNIQUE` (`item_name`),
  KEY `category_fk_idx` (`category_id`),
  KEY `type_fk_idx` (`type_id`),
  CONSTRAINT `menu_mst_category_fk` FOREIGN KEY (`category_id`) REFERENCES `food_category` (`id`),
  CONSTRAINT `menu_mst_type_fk` FOREIGN KEY (`type_id`) REFERENCES `food_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_mst`
--

LOCK TABLES `menu_mst` WRITE;
/*!40000 ALTER TABLE `menu_mst` DISABLE KEYS */;
INSERT INTO `menu_mst` VALUES (1,'checken65',2,2,15,NULL,100,NULL),(2,'checken tikka',2,2,15,NULL,100,NULL),(3,'chilli chicken',2,2,15,NULL,100,NULL),(4,'aloo tikka',1,1,13,NULL,75,NULL),(5,'gobi manchuria',1,1,13,NULL,75,NULL),(6,'panneer 65',1,1,13,NULL,75,NULL),(7,'chicken biryani',4,2,20,NULL,150,NULL),(8,'mutton biryani',4,2,20,NULL,150,NULL),(9,'cheicken kolhapuri',4,2,20,NULL,150,NULL),(10,'checken keema',4,2,20,NULL,150,NULL),(11,'samosa',4,5,10,NULL,50,NULL),(12,'kachori',4,5,10,NULL,50,NULL),(13,'ice cream',4,5,5,NULL,50,NULL),(14,'soft drink',5,5,1,NULL,50,NULL);
/*!40000 ALTER TABLE `menu_mst` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-19  0:33:06
