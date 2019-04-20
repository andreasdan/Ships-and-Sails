CREATE DATABASE `modulopgave3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
CREATE TABLE `actiontype` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ammunition` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `ammoType` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ammoType` (`ammoType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `directions` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `directionName` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `directionName` (`directionName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `guns` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `noRowofGun` int(1) NOT NULL,
  `noGunPrRow` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roundorders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `shipsId` int(10) NOT NULL,
  `round` int(10) NOT NULL,
  `actionTypeId` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKRoundOrder195912` (`shipsId`),
  KEY `FKRoundOrder301087` (`actionTypeId`),
  CONSTRAINT `FKRoundOrder195912` FOREIGN KEY (`shipsId`) REFERENCES `ship` (`id`),
  CONSTRAINT `FKRoundOrder301087` FOREIGN KEY (`actionTypeId`) REFERENCES `actiontype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sails` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `maxNoOfSail` int(2) NOT NULL,
  `maxSails` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `scenario` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `size` int(10) NOT NULL,
  `noOfShipPrPlayer` int(2) NOT NULL,
  `noOfShipPrType` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `size` (`size`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ship` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `nationality` varchar(20) NOT NULL,
  `typesId` int(1) NOT NULL,
  `Directionsid` int(1) NOT NULL,
  `Ammunitionid` int(1) NOT NULL,
  `positionX` int(2) NOT NULL,
  `positionY` int(2) NOT NULL,
  `currentHull` int(3) NOT NULL,
  `currentSailor` int(3) NOT NULL,
  `currentSpeed` int(1) NOT NULL,
  `currentSailHealth` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKShip904437` (`typesId`),
  KEY `FKShip382211` (`Directionsid`),
  KEY `FKShip745294` (`Ammunitionid`),
  CONSTRAINT `FKShip382211` FOREIGN KEY (`Directionsid`) REFERENCES `directions` (`id`),
  CONSTRAINT `FKShip745294` FOREIGN KEY (`Ammunitionid`) REFERENCES `ammunition` (`id`),
  CONSTRAINT `FKShip904437` FOREIGN KEY (`typesId`) REFERENCES `shiptype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `shiptype` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `maxSailor` int(3) NOT NULL,
  `maxHull` int(3) NOT NULL,
  `noOfTurn` int(1) NOT NULL,
  `noRowofGun` int(1) NOT NULL,
  `noGunPrRow` int(2) NOT NULL,
  `maxNoOfSail` int(2) NOT NULL,
  `maxSails` int(2) NOT NULL,
  `maxSpeed` int(1) NOT NULL,
  `maxSpeedChange` int(1) NOT NULL,
  `GunId` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `speed` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `maxSpeed` int(1) NOT NULL,
  `maxSpeedChange` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;