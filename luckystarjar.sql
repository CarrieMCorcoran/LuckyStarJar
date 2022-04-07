-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: luckystarjar
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `jar`
--

DROP TABLE IF EXISTS `jar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jar` (
  `jarID` int NOT NULL AUTO_INCREMENT,
  `openDate` date DEFAULT NULL,
  `remFreq` int DEFAULT NULL,
  `inviteCode` int DEFAULT NULL,
  `jarName` varchar(55) DEFAULT NULL,
  `lastReminderDate` date DEFAULT NULL,
  `wasOpened` int DEFAULT NULL,
  PRIMARY KEY (`jarID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jar`
--

LOCK TABLES `jar` WRITE;
/*!40000 ALTER TABLE `jar` DISABLE KEYS */;
INSERT INTO `jar` VALUES (1,'2022-12-31',1,12345,'newJar','2022-04-03',0);
/*!40000 ALTER TABLE `jar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note` (
  `noteID` int NOT NULL AUTO_INCREMENT,
  `noteText` longtext,
  `noteDate` date DEFAULT NULL,
  `userJarID` int DEFAULT NULL,
  PRIMARY KEY (`noteID`),
  KEY `userJarID` (`userJarID`),
  CONSTRAINT `note_ibfk_1` FOREIGN KEY (`userJarID`) REFERENCES `userjar` (`userJarID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES (1,'I like cheese','2022-03-22',6),(3,'May the Force be With You','2022-03-24',1);
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(25) DEFAULT NULL,
  `userGoogleAuthInfo` varchar(55) DEFAULT NULL,
  `userEmail` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Carrie','I like cheese','ccorcor3@oswego.edu'),(2,'Alaina','2','amcmaho3@oswego.edu'),(3,'Ben','4','bmelby@oswego.edu'),(4,'Jessie','2','jgodden@oswego.edu'),(5,'Kaylee','whatever','kaylee@cat.com'),(6,'Sapphire','sure','sapphire@garnet.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userjar`
--

DROP TABLE IF EXISTS `userjar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userjar` (
  `userJarID` int NOT NULL AUTO_INCREMENT,
  `jarID` int DEFAULT NULL,
  `notecolor` int DEFAULT NULL,
  `userID` int DEFAULT NULL,
  `isOwner` int DEFAULT NULL,
  PRIMARY KEY (`userJarID`),
  KEY `jarID` (`jarID`),
  KEY `userID` (`userID`),
  CONSTRAINT `userjar_ibfk_1` FOREIGN KEY (`jarID`) REFERENCES `jar` (`jarID`),
  CONSTRAINT `userjar_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userjar`
--

LOCK TABLES `userjar` WRITE;
/*!40000 ALTER TABLE `userjar` DISABLE KEYS */;
INSERT INTO `userjar` VALUES (1,1,1,1,1),(6,1,4,2,0),(9,1,4,4,0),(18,1,12,3,0);
/*!40000 ALTER TABLE `userjar` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-06 20:04:19
