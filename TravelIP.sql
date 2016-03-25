-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: localhost    Database: travel
-- ------------------------------------------------------
-- Server version	5.7.10-log

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
-- Table structure for table `contacts`
--
use travel;
DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `contact` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contacts_user1_idx` (`user`),
  KEY `fk_contacts_user2_idx` (`contact`),
  CONSTRAINT `fk_contacts_user1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_contacts_user2` FOREIGN KEY (`contact`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  `read` int(11) NOT NULL,
  `from` int(11) NOT NULL,
  `to` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_messages_user1_idx` (`from`),
  KEY `fk_messages_user2_idx` (`to`),
  CONSTRAINT `fk_messages_user1` FOREIGN KEY (`from`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_user2` FOREIGN KEY (`to`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) NOT NULL,
  `uploader` int(11) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '0',
  `travel` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_photo_user1_idx` (`uploader`),
  KEY `fk_photo_travels1_idx` (`travel`),
  CONSTRAINT `fk_photo_travels1` FOREIGN KEY (`travel`) REFERENCES `travels` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_photo_user1` FOREIGN KEY (`uploader`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo`
--

LOCK TABLES `photo` WRITE;
/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
INSERT INTO `photo` VALUES (1,'C:\\ipPhotos\\3\\travel with photo test\\',3,0,6),(2,'C:\\ipPhotos\\3\\travel with photo test\\Alien1.bmp',3,1,1),(3,'/photos/',3,0,7),(4,'/photos/',3,0,8),(5,'/TravelTheWorldAround/photos/',3,0,9),(6,'C:\\Users\\Milos\\workspace\\TravelTheWorldAroundIP2016\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp3\\wtpwebapps\\TravelTheWorldAround\\Alien2.bmp',3,0,10),(7,'C:\\Users\\Milos\\workspace\\TravelTheWorldAroundIP2016\\TravelTheWorldAround\\WebContent\\photos\\',3,0,11),(8,'/photos/3/asded33/Chocolate Cake.bmp',3,0,13),(9,'C:\\Users\\Milos\\workspace\\TravelTheWorldAroundIP2016\\TravelTheWorldAround\\WebContent\\photos\\3\\Generalna proba\\Alien 1.bmp',3,0,14),(10,'/photos/3/Proba/Alien 1.bmp',3,0,15),(11,'/photos/3/Proba/Alien 2.bmp',3,0,15);
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo_review`
--

DROP TABLE IF EXISTS `photo_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photo_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reviewer` int(11) NOT NULL,
  `photo` int(11) NOT NULL,
  `text` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_has_photo_photo1_idx` (`photo`),
  KEY `fk_user_has_photo_user1_idx` (`reviewer`),
  CONSTRAINT `fk_user_has_photo_photo1` FOREIGN KEY (`photo`) REFERENCES `photo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_photo_user1` FOREIGN KEY (`reviewer`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo_review`
--

LOCK TABLES `photo_review` WRITE;
/*!40000 ALTER TABLE `photo_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `photo_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilegies`
--

DROP TABLE IF EXISTS `privilegies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privilegies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='dd		ddd';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilegies`
--

LOCK TABLES `privilegies` WRITE;
/*!40000 ALTER TABLE `privilegies` DISABLE KEYS */;
INSERT INTO `privilegies` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `privilegies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travels`
--

DROP TABLE IF EXISTS `travels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `text` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `location` varchar(45) NOT NULL,
  `author` int(11) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '0',
  `keywords` varchar(45) NOT NULL,
  `shared` int(11) NOT NULL DEFAULT '0',
  `brief` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_travels_user1_idx` (`author`),
  CONSTRAINT `fk_travels_user1` FOREIGN KEY (`author`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travels`
--

LOCK TABLES `travels` WRITE;
/*!40000 ALTER TABLE `travels` DISABLE KEYS */;
INSERT INTO `travels` VALUES (1,'aaa','asd','2016-01-23 20:58:46','asdas',3,1,'asd',0,'asd'),(2,'asdasd','asd','2016-01-23 21:14:55','sdsd',3,0,'sds',0,'sdsd'),(3,'a1','a1','2016-01-23 21:44:33','a1',3,0,'a1',0,'a1'),(4,'aaa','asdas','2016-01-24 23:41:16','aaa',3,0,'aaa',0,'asd'),(5,'test sa pathom od silka','test sa pathom','2016-02-01 13:58:32','test',3,0,'test',0,'path'),(6,'travel with photo test','test','2016-02-01 14:23:09','TEST',3,0,'TEST',0,'test'),(7,'nest','dfsdf','2016-02-12 13:44:33','sss',3,0,'ss',0,'sdf'),(8,'adasd','asds','2016-02-12 13:55:24','asds',3,0,'asdds',0,'ss'),(9,'asd','asd','2016-02-12 14:08:52','asd',3,0,'asd',0,'asd'),(10,'3423','234','2016-02-12 14:27:12','23423',3,0,'234',0,'34'),(11,'sd222','sdaf','2016-02-12 16:02:59','asdw2',3,0,'sdsd2232',0,'aa'),(13,'asded33','qaw','2016-02-12 16:17:57','1234',3,1,'e23e',0,'as'),(14,'Generalna proba','Proba','2016-02-14 16:10:13','ovo je proba uploada',3,0,'generalna proba',0,'banja Luka'),(15,'Proba','proba','2016-02-14 16:14:22','proba',3,1,'proba',0,'Banja Luka');
/*!40000 ALTER TABLE `travels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travels_review`
--

DROP TABLE IF EXISTS `travels_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travels_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reviwer` int(11) NOT NULL,
  `review` int(11) NOT NULL,
  `text` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_has_travels_travels1_idx` (`review`),
  KEY `fk_user_has_travels_user1_idx` (`reviwer`),
  CONSTRAINT `fk_user_has_travels_travels1` FOREIGN KEY (`review`) REFERENCES `travels` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_travels_user1` FOREIGN KEY (`reviwer`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travels_review`
--

LOCK TABLES `travels_review` WRITE;
/*!40000 ALTER TABLE `travels_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `travels_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `privilegies_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `biography` varchar(45) NOT NULL,
  `birthday` datetime NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_user_privilegies_idx` (`privilegies_id`),
  CONSTRAINT `fk_user_privilegies` FOREIGN KEY (`privilegies_id`) REFERENCES `privilegies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'admin','admin','ortocelt@gmail.com','Application administrator','1985-07-09 00:00:00','admin','21232f297a57a5a743894a0e4a801fc3',1),(3,2,'Milos','Jankovic','ortocelt@gmail.com','Obicni user','1996-12-31 01:00:00','milos','05735bf2bdcadbd5151bd1a2113f36b4',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-24  8:18:50
