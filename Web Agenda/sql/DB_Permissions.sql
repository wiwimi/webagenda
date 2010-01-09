/**
 * Script created by WebAgenda Team 2009
 * 
 * Creates tables required for permission sets for the employee, the schedule, and
 * the system.
 * Adds constraints and inserts data all in one script. Once stable, this script
 * probably won't change.
 * 
 * Version 0.01.00
 *
 */

-- Drop any existing older versions of the created tables

DROP TABLE IF EXISTS `webagenda`.`perm_schedule`;
DROP TABLE IF EXISTS `webagenda`,`perm_employee`;
DROP TABLE IF EXISTS `webagenda`,`perm_system`;

-- Create tables with Primary key column

CREATE TABLE `webagenda`.`perm_employee` (
`name`			VARCHAR(50),
`value`			VARCHAR(20) NOT NULL DEFAULT ' ',
PRIMARY KEY (name)
);

CREATE TABLE `webagenda`.`perm_schedule` (
`name`			VARCHAR(50),
`value`			VARCHAR(20) NOT NULL DEFAULT ' ',
PRIMARY KEY (name)
);

CREATE TABLE `webagenda`.`perm_system` (
`name`			VARCHAR(50),
`value`			VARCHAR(20) NOT NULL DEFAULT ' ',
PRIMARY KEY (name)
);

-- Put defaults into values, should reflect permission level 0

-- Employee Permission Set
INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanReadActiveSched','true');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanReadPreviousSched','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanReadFutureSched','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanEditActiveSched','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanEditFutureSched','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanViewResources','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanSearchResources','true');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanChangePermissions','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanReadLogs','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanCreateReports','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanReadReports','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanExportReports','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanExportSchedule','true');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanRequestDaysOff','true');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('MaxDaysOff','3');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanTakeVacations','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('MaxVacationDays','0');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanTakeEmergencyDaysOff','true');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanViewInactiveEmployees','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('CanSendNotifications','true');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('Trusted','false');

INSERT INTO `webagenda`.`perm_employee`
VALUES ('PreferredRank','0');

INSERT INTO `webagenda`.`perm_schedule`
VALUES ('LowestReadPermission','0');

INSERT INTO `webagenda`.`perm_schedule`
VALUES ('DefaultTimeRef','M');

INSERT INTO `webagenda`.`perm_schedule`
VALUES ('Active',DATE_FORMAT(NOW() + 14,'%Y-%m-%d'));

INSERT INTO `webagenda`.`perm_schedule`
VALUES('ScheduleDuration','14');

INSERT INTO `webagenda`.`perm_schedule`
VALUES ('EmployeeCreator','-1');

INSERT INTO `webagenda`.`perm_system`
VALUES('DenyShiftRequest',NOW());

INSERT INTO `webagenda`.`perm_system`
VALUES ('MinShiftDuration','03:00');

INSERT INTO `webagenda`.`perm_system`
VALUES ('MinAvailablilityShifts','0');

INSERT INTO `webagenda`.`perm_system`
VALUES ('AvailabilityChangePause',DATE_FORMAT(NOW() + 14,'%Y-%m-%d'));

INSERT INTO `webagenda`.`perm_system`
VALUES ('SeniorityRule','1');

INSERT INTO `webagenda`.`perm_system`
VALUES ('MaxEmergencyDuration','14');