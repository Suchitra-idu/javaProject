-- MySQL dump 10.13  Distrib 8.4.0, for Win64 (x86_64)
--
-- Host: localhost    Database: resturant
-- ------------------------------------------------------
-- Server version	8.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `empID` int NOT NULL,
  `name` text,
  `email` text,
  `password` text,
  `phoneNumber` text,
  `empKey` text,
  `role` text,
  PRIMARY KEY (`empID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (34,'ff','wfw','wf','wf','wf','cook'),(45,'gamita','dsv','123','34','123','waiter'),(45632,'Tathsara','tathsara@gmail.com','iamdumb','119','21','cook');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `foodId` int NOT NULL,
  `foodName` varchar(25) DEFAULT NULL,
  `price` float DEFAULT NULL,
  PRIMARY KEY (`foodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,'salad',300),(2,'burger',500),(3,'pizza',1200),(4,'french fries',400),(5,'chicken wings',800);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `TableId` text,
  `orderdDate` date DEFAULT NULL,
  `orderdTime` time DEFAULT NULL,
  `additionalInfo` text,
  `compleateTime` time DEFAULT NULL,
  `currentState` text,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=507155820 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1295969,'TA','2024-05-14','11:22:43','dont add potatos',NULL,'Finish order'),(6559278,'TI','2024-05-15','13:39:37','tomatos',NULL,'pending'),(7653344,'TG','2024-05-15','17:57:28','',NULL,'pending'),(7975951,'TF','2024-05-15','13:24:10','',NULL,'Finish order');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderfood`
--

DROP TABLE IF EXISTS `orderfood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderfood` (
  `orderFoodId` int NOT NULL AUTO_INCREMENT,
  `quntity` int DEFAULT NULL,
  `orderId` int DEFAULT NULL,
  `foodId` int DEFAULT NULL,
  `state` text,
  `cookId` int DEFAULT NULL,
  PRIMARY KEY (`orderFoodId`),
  KEY `orderfood_ibfk_1` (`orderId`),
  KEY `foodId` (`foodId`),
  CONSTRAINT `orderfood_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `order` (`orderId`),
  CONSTRAINT `orderfood_ibfk_2` FOREIGN KEY (`foodId`) REFERENCES `food` (`foodId`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderfood`
--

LOCK TABLES `orderfood` WRITE;
/*!40000 ALTER TABLE `orderfood` DISABLE KEYS */;
INSERT INTO `orderfood` VALUES (48,1,1295969,1,'Serving',45632),(49,1,1295969,4,'Serving',45632),(50,1,1295969,5,'Serving',45632),(51,1,7975951,3,'Serving',45632),(52,2,7975951,4,'Serving',45632),(53,1,6559278,1,'Pending',NULL),(54,1,6559278,2,'Pending',NULL),(55,1,7653344,1,'Cancelled',NULL),(56,1,7653344,2,'Pending',NULL),(57,1,7653344,3,'Pending',NULL);
/*!40000 ALTER TABLE `orderfood` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-17  9:33:02
