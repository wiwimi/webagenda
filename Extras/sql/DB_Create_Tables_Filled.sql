SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `WebAgenda` ;
CREATE SCHEMA IF NOT EXISTS `WebAgenda` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

-- -----------------------------------------------------
-- Table `WebAgenda`.`PERMISSIONSET`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`PERMISSIONSET` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`PERMISSIONSET` (
  `plevel` VARCHAR(10) NOT NULL ,
  `canEditSched` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canReadSched` TINYINT(1)  NOT NULL DEFAULT 1 ,
  `canReadOldSched` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canManageEmployee` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canViewResources` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canChangePermissions` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canReadLogs` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canAccessReports` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canRequestDaysOff` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `maxDaysOff` INT NOT NULL ,
  `canTakeVacations` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `maxVacationDays` INT NOT NULL ,
  `canTakeEmergencyDays` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canViewInactiveEmps` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `canSendNotifications` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `trusted` VARCHAR(10) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`plevel`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`LOCATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`LOCATION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`LOCATION` (
  `locName` VARCHAR(45) NOT NULL ,
  `locDescription` VARCHAR(200) NULL ,
  PRIMARY KEY (`locName`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`POSITION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`POSITION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`POSITION` (
  `positionName` VARCHAR(45) NOT NULL ,
  `positionDescription` VARCHAR(200) NULL ,
  PRIMARY KEY (`positionName`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`EMPLOYEE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`EMPLOYEE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`EMPLOYEE` (
  `empID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `supID` INT UNSIGNED NULL ,
  `givenName` VARCHAR(70) NOT NULL ,
  `familyName` VARCHAR(70) NOT NULL ,
  `birthDate` DATE NULL ,
  `email` VARCHAR(50) NULL ,
  `username` VARCHAR(20) NOT NULL ,
  `password` VARCHAR(8) NOT NULL ,
  `lastLogin` TIMESTAMP NULL ,
  `prefPosition` VARCHAR(45) NULL ,
  `prefLocation` VARCHAR(45) NULL ,
  `plevel` VARCHAR(10) NOT NULL ,
  `active` TINYINT(1)  NOT NULL DEFAULT 1 ,
  `passChanged` TINYINT(1)  NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`empID`) ,
  INDEX `fk_EMPLOYEE_PERMISSIONSET` (`plevel` ASC) ,
  INDEX `fk_EMPLOYEE_LOCATION` (`prefLocation` ASC) ,
  INDEX `fk_EMPLOYEE_POSITION` (`prefPosition` ASC) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  INDEX `empID_IDX` (`empID` ASC) ,
  INDEX `fk_EMPLOYEE_SUPERVISOR` (`supID` ASC) ,
  CONSTRAINT `fk_EMPLOYEE_PERMISSIONSET`
    FOREIGN KEY (`plevel` )
    REFERENCES `WebAgenda`.`PERMISSIONSET` (`plevel` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPLOYEE_LOCATION`
    FOREIGN KEY (`prefLocation` )
    REFERENCES `WebAgenda`.`LOCATION` (`locName` )
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPLOYEE_POSITION`
    FOREIGN KEY (`prefPosition` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPLOYEE_SUPERVISOR`
    FOREIGN KEY (`supID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`NOTIFICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`NOTIFICATION` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`NOTIFICATION` (
  `notificationID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `senderID` INT UNSIGNED NULL ,
  `recipientID` INT UNSIGNED NOT NULL ,
  `sentTime` TIMESTAMP NOT NULL ,
  `viewed` TINYINT(1)  NOT NULL DEFAULT 0 ,
  `message` VARCHAR(300) NOT NULL ,
  `type` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`notificationID`) ,
  INDEX `fk_NOTIFICATION_SENDER` (`senderID` ASC) ,
  INDEX `fk_NOTIFICATION_RECIPIENT` (`recipientID` ASC) ,
  CONSTRAINT `fk_NOTIFICATION_SENDER`
    FOREIGN KEY (`senderID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_NOTIFICATION_RECIPIENT`
    FOREIGN KEY (`recipientID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SKILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SKILL` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SKILL` (
  `skillName` VARCHAR(45) NOT NULL ,
  `skillDescription` VARCHAR(200) NULL ,
  PRIMARY KEY (`skillName`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`EMPSKILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`EMPSKILL` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`EMPSKILL` (
  `empID` INT UNSIGNED NOT NULL ,
  `skillName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`empID`, `skillName`) ,
  INDEX `fk_EMPSKILL_SKILL` (`skillName` ASC) ,
  INDEX `fk_EMPSKILL_EMPLOYEE` (`empID` ASC) ,
  CONSTRAINT `fk_EMPSKILL_SKILL`
    FOREIGN KEY (`skillName` )
    REFERENCES `WebAgenda`.`SKILL` (`skillName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EMPSKILL_EMPLOYEE`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`POSSKILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`POSSKILL` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`POSSKILL` (
  `positionName` VARCHAR(45) NOT NULL ,
  `skillName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`positionName`, `skillName`) ,
  INDEX `fk_POSSKILL_POSITION` (`positionName` ASC) ,
  INDEX `fk_POSSKILL_SKILL` (`skillName` ASC) ,
  CONSTRAINT `fk_POSSKILL_POSITION`
    FOREIGN KEY (`positionName` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_POSSKILL_SKILL`
    FOREIGN KEY (`skillName` )
    REFERENCES `WebAgenda`.`SKILL` (`skillName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SCHEDULETEMPLATE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SCHEDULETEMPLATE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SCHEDULETEMPLATE` (
  `schedTempID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `creatorID` INT UNSIGNED NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`schedTempID`) ,
  INDEX `fk_SCHEDULETEMPLATE_EMPLOYEE` (`creatorID` ASC) ,
  UNIQUE INDEX `SCHEDULETEMPLATE_UNIQUE` (`creatorID` ASC, `name` ASC) ,
  CONSTRAINT `fk_SCHEDULETEMPLATE_EMPLOYEE`
    FOREIGN KEY (`creatorID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTTEMPLATE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTTEMPLATE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTTEMPLATE` (
  `shiftTempID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `schedTempID` INT UNSIGNED NOT NULL ,
  `day` INT UNSIGNED NOT NULL ,
  `startTime` TIME NOT NULL ,
  `endTime` TIME NOT NULL ,
  PRIMARY KEY (`shiftTempID`) ,
  INDEX `fk_SHIFT_SHIFTPOS` (`schedTempID` ASC) ,
  UNIQUE INDEX `SHIFTTEMPLATE_UNIQUE` (`schedTempID` ASC, `day` ASC, `startTime` ASC, `endTime` ASC) ,
  CONSTRAINT `fk_SHIFT_SHIFTPOS`
    FOREIGN KEY (`schedTempID` )
    REFERENCES `WebAgenda`.`SCHEDULETEMPLATE` (`schedTempID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTPOS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTPOS` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTPOS` (
  `shiftTempID` INT UNSIGNED NOT NULL ,
  `positionName` VARCHAR(45) NOT NULL ,
  `posCount` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`shiftTempID`, `positionName`) ,
  INDEX `fk_SHIFTPOS_SHIFTTEMPLATE` (`shiftTempID` ASC) ,
  INDEX `fk_SHIFTPOS_POSITION` (`positionName` ASC) ,
  CONSTRAINT `fk_SHIFTPOS_SHIFTTEMPLATE`
    FOREIGN KEY (`shiftTempID` )
    REFERENCES `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SHIFTPOS_POSITION`
    FOREIGN KEY (`positionName` )
    REFERENCES `WebAgenda`.`POSITION` (`positionName` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SCHEDULE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SCHEDULE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SCHEDULE` (
  `schedID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `creatorID` INT UNSIGNED NOT NULL ,
  `startDate` DATE NOT NULL ,
  `endDate` DATE NOT NULL ,
  PRIMARY KEY (`schedID`) ,
  INDEX `fk_SCHEDULE_EMPLOYEE` (`creatorID` ASC) ,
  CONSTRAINT `fk_SCHEDULE_EMPLOYEE`
    FOREIGN KEY (`creatorID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFT` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFT` (
  `shiftID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `schedID` INT UNSIGNED NOT NULL ,
  `day` INT UNSIGNED NOT NULL ,
  `startTime` TIME NOT NULL ,
  `endTime` TIME NOT NULL ,
  PRIMARY KEY (`shiftID`) ,
  INDEX `fk_SHIFT_SCHEDULE` (`schedID` ASC) ,
  UNIQUE INDEX `SCHEDULE_UNIQUE` (`schedID` ASC, `day` ASC, `startTime` ASC, `endTime` ASC) ,
  CONSTRAINT `fk_SHIFT_SCHEDULE`
    FOREIGN KEY (`schedID` )
    REFERENCES `WebAgenda`.`SCHEDULE` (`schedID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`SHIFTEMP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`SHIFTEMP` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`SHIFTEMP` (
  `shiftID` INT UNSIGNED NOT NULL ,
  `empID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`shiftID`, `empID`) ,
  INDEX `fk_SHIFTEMP_SHIFT` (`shiftID` ASC) ,
  INDEX `fk_SHIFTEMP_EMPLOYEE` (`empID` ASC) ,
  CONSTRAINT `fk_SHIFTEMP_SHIFT`
    FOREIGN KEY (`shiftID` )
    REFERENCES `WebAgenda`.`SHIFT` (`shiftID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SHIFTEMP_EMPLOYEE`
    FOREIGN KEY (`empID` )
    REFERENCES `WebAgenda`.`EMPLOYEE` (`empID` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`GLOBALSETTINGS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`GLOBALSETTINGS` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`GLOBALSETTINGS` (
  `tempKey` INT NOT NULL ,
  PRIMARY KEY (`tempKey`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WebAgenda`.`RULE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `WebAgenda`.`RULE` ;

CREATE  TABLE IF NOT EXISTS `WebAgenda`.`RULE` (
  `ruleID` INT NOT NULL AUTO_INCREMENT ,
  `shiftTempID` INT UNSIGNED NOT NULL ,
  `ruleType` VARCHAR(45) NOT NULL ,
  `ruleValue` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ruleID`) ,
  INDEX `fk_RULE_SHIFTTEMPLATE` (`shiftTempID` ASC) ,
  CONSTRAINT `fk_RULE_SHIFTTEMPLATE`
    FOREIGN KEY (`shiftTempID` )
    REFERENCES `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- procedure CREATEEMPLOYEE
-- -----------------------------------------------------

DELIMITER $$
DROP procedure IF EXISTS `WebAgenda`.`CREATEEMPLOYEE` $$
CREATE PROCEDURE `WebAgenda`.`CREATEEMPLOYEE`
    (IN inEmpID INT, IN inSupID INT, IN inGivenName VARCHAR(70), IN inFamilyName VARCHAR(70),
    IN inBirthDate DATE, IN inEmail VARCHAR(50), IN inUsername VARCHAR(20), IN inPassword VARCHAR(8),
    IN inPosition VARCHAR(45), IN inLocation VARCHAR(45), IN inPLevel VARCHAR(10), OUT result BOOLEAN)
BEGIN
    DECLARE numRows INT;
    
    INSERT INTO `WebAgenda`.`EMPLOYEE`
        (`empID`,`supID`,`givenName`,`familyName`,`birthDate`,`email`,
        `username`,`password`,`prefPosition`,`prefLocation`,`plevel`)
    VALUES
        (inEmpID,inSupID,inGivenName,inFamilyName,inBirthDate,inEmail,
        inUsername,inPassword,inPosition,inLocation,inPLevel);
    
    SET numRows = ROW_COUNT();
    
    IF numROWS > 0 THEN
        SET result = TRUE;
    ELSE
        SET result = FALSE;
    END IF;
END $$

DELIMITER ;

DROP USER 'WABroker'@'localhost';
CREATE USER 'WABroker'@'localhost' IDENTIFIED BY 'password';

grant SELECT ON `mysql`.`proc` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`EMPLOYEE` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`EMPLOYEE` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`EMPLOYEE` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`EMPLOYEE` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`EMPSKILL` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`EMPSKILL` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`EMPSKILL` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`EMPSKILL` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`GLOBALSETTINGS` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`GLOBALSETTINGS` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`GLOBALSETTINGS` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`GLOBALSETTINGS` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`LOCATION` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`LOCATION` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`LOCATION` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`LOCATION` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`NOTIFICATION` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`NOTIFICATION` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`NOTIFICATION` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`NOTIFICATION` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`PERMISSIONSET` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`PERMISSIONSET` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`PERMISSIONSET` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`PERMISSIONSET` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`POSITION` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`POSITION` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`POSITION` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`POSITION` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`POSSKILL` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`POSSKILL` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`POSSKILL` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`POSSKILL` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SCHEDULE` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SCHEDULE` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SCHEDULE` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SCHEDULE` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SCHEDULETEMPLATE` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SCHEDULETEMPLATE` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SCHEDULETEMPLATE` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SCHEDULETEMPLATE` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SHIFTTEMPLATE` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SHIFTTEMPLATE` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SHIFTTEMPLATE` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SHIFTTEMPLATE` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SHIFTPOS` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SHIFTPOS` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SHIFTPOS` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SHIFTPOS` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SKILL` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SKILL` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SKILL` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SKILL` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SHIFTEMP` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SHIFTEMP` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SHIFTEMP` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SHIFTEMP` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`SHIFT` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`SHIFT` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`SHIFT` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`SHIFT` to 'WABroker'@'localhost';
grant EXECUTE on procedure `WebAgenda`.`CREATEEMPLOYEE` to 'WABroker'@'localhost';
grant DELETE on TABLE `WebAgenda`.`RULE` to 'WABroker'@'localhost';
grant INSERT on TABLE `WebAgenda`.`RULE` to 'WABroker'@'localhost';
grant SELECT on TABLE `WebAgenda`.`RULE` to 'WABroker'@'localhost';
grant UPDATE on TABLE `WebAgenda`.`RULE` to 'WABroker'@'localhost';

FLUSH PRIVILEGES;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`PERMISSIONSET`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`PERMISSIONSET` (`plevel`, `canEditSched`, `canReadSched`, `canReadOldSched`, `canManageEmployee`, `canViewResources`, `canChangePermissions`, `canReadLogs`, `canAccessReports`, `canRequestDaysOff`, `maxDaysOff`, `canTakeVacations`, `maxVacationDays`, `canTakeEmergencyDays`, `canViewInactiveEmps`, `canSendNotifications`, `trusted`) values ('1a', false, true, false, false, false, false, false, false, false, 0, false, 0, true, false, false, '1a');
insert into `WebAgenda`.`PERMISSIONSET` (`plevel`, `canEditSched`, `canReadSched`, `canReadOldSched`, `canManageEmployee`, `canViewResources`, `canChangePermissions`, `canReadLogs`, `canAccessReports`, `canRequestDaysOff`, `maxDaysOff`, `canTakeVacations`, `maxVacationDays`, `canTakeEmergencyDays`, `canViewInactiveEmps`, `canSendNotifications`, `trusted`) values ('2a', true, true, true, true, true, true, true, true, true, 5, true, 20, true, true, true, '2a');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`LOCATION`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`LOCATION` (`locName`, `locDescription`) values ('Mohave Grill', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`POSITION`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('General Manager', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Executive Chef', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Front of House Mgr.', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Cook', NULL);
insert into `WebAgenda`.`POSITION` (`positionName`, `positionDescription`) values ('Waiter', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`EMPLOYEE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (12314, NULL, 'Chaney', 'Henson', NULL, NULL, 'user1', 'password', NULL, 'General Manager', 'Mohave Grill', '2a', true, 1);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (28472, 12314, 'Ray', 'Oliver', NULL, NULL, 'user2', 'password', NULL, 'Executive Chef', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (29379, 12314, 'Audra', 'Gordon', NULL, NULL, 'user3', 'password', NULL, 'Front of House Mgr.', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (38382, 28472, 'Rina', 'Pruitt', NULL, NULL, 'user4', 'password', NULL, 'Cook', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (38202, 28472, 'Quinn', 'Hart', NULL, NULL, 'user5', 'password', NULL, 'Cook', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (39280, 28472, 'Sierra', 'Dean', NULL, NULL, 'user6', 'password', NULL, 'Cook', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (39202, 29379, 'Sylvia', 'Dyer', NULL, NULL, 'user7', 'password', NULL, 'Waiter', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (39203, 29379, 'Kay', 'Bates', NULL, NULL, 'user8', 'password', NULL, 'Waiter', 'Mohave Grill', '1a', true, 0);
insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`, `passChanged`) values (30293, 29379, 'Luke', 'Garrison', NULL, NULL, 'user9', 'password', NULL, 'Waiter', 'Mohave Grill', '1a', true, 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SKILL`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('Serving', NULL);
insert into `WebAgenda`.`SKILL` (`skillName`, `skillDescription`) values ('Cooking', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`EMPSKILL`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (28472, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (38382, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (38202, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (39280, 'Cooking');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (39202, 'Serving');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (39203, 'Serving');
insert into `WebAgenda`.`EMPSKILL` (`empID`, `skillName`) values (30293, 'Serving');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`POSSKILL`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Executive Chef', 'Cooking');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Cook', 'Cooking');
insert into `WebAgenda`.`POSSKILL` (`positionName`, `skillName`) values ('Waiter', 'Serving');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SCHEDULETEMPLATE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SCHEDULETEMPLATE` (`schedTempID`, `creatorID`, `name`) values (1, 12314, 'Test Template');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFTTEMPLATE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (1, 1, 2, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (2, 1, 3, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (3, 1, 4, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (4, 1, 5, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFTTEMPLATE` (`shiftTempID`, `schedTempID`, `day`, `startTime`, `endTime`) values (5, 1, 6, '08:00:00', '17:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFTPOS`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (1, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (1, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (2, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (2, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (3, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (3, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (4, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (4, 'Waiter', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (5, 'Cook', 3);
insert into `WebAgenda`.`SHIFTPOS` (`shiftTempID`, `positionName`, `posCount`) values (5, 'Waiter', 3);

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SCHEDULE`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SCHEDULE` (`schedID`, `creatorID`, `startDate`, `endDate`) values (1, 12314, '2010-04-18', '2010-04-24');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFT`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (1, 1, 2, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (2, 1, 3, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (3, 1, 4, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (4, 1, 5, '08:00:00', '17:00:00');
insert into `WebAgenda`.`SHIFT` (`shiftID`, `schedID`, `day`, `startTime`, `endTime`) values (5, 1, 6, '08:00:00', '17:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `WebAgenda`.`SHIFTEMP`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (1, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (2, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (3, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (4, 30293);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 38382);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 38202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 39280);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 39202);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 39203);
insert into `WebAgenda`.`SHIFTEMP` (`shiftID`, `empID`) values (5, 30293);

COMMIT;
