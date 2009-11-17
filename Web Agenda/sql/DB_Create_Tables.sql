/*
 * Creates all user information tables without constraints.
 * All constraints will be aded by the DB_Alter_Tables
 * script.
 * 
 * Created by Daniel Wehr, WebAgenda Team 2009
 * Revision 0.4
 * - Moved username and password to user table.
 * - Renamed login table to user_stats.
 * - Added lastPassChange to user_stats.
 * - Created user_extras table.
 * Revision 0.3
 * - Renamed the 'job' column in the working_jobs table to 'jobID'
 *   so that it matches its equivalent column in the job_type table.
 * Revision 0.2
 * - Removed unneeded foreign keys.
 * Revision 0.1
 * - Table creation code completed.
 */

DROP TABLE IF EXISTS `webagenda`.`user`;
DROP TABLE IF EXISTS `webagenda`.`address`;
DROP TABLE IF EXISTS `webagenda`.`phone`;
DROP TABLE IF EXISTS `webagenda`.`email`;
DROP TABLE IF EXISTS `webagenda`.`user_stats`;
DROP TABLE IF EXISTS `webagenda`.`working_jobs`;
DROP TABLE IF EXISTS `webagenda`.`job_type`;

CREATE TABLE `webagenda`.`user`
(`userID` 			VARCHAR(10),
`firstName` 		VARCHAR(20),
`lastName` 			VARCHAR(20),
`username`			VARCHAR(30),
`password`			VARCHAR(30),
`userEnabled` 		BIT);

CREATE TABLE `webagenda`.`address`
(`userID`			VARCHAR(10),
`address`			VARCHAR(100),
`city`				VARCHAR(30),
`postalCode`		VARCHAR(8),
`country`			VARCHAR(40));

CREATE TABLE `webagenda`.`phone`
(`userID`			VARCHAR(10),
`phoneType`			VARCHAR(25),
`phoneNumber`		CHAR(10));

CREATE TABLE `webagenda`.`email`
(`userID`			VARCHAR(10),
`emailType`			VARCHAR(25),
`email`				VARCHAR(50));

CREATE TABLE `webagenda`.`user_stats`
(`userID`			VARCHAR(10),
`lastLogin`			DATETIME,
`lastPassChange`	DATETIME);

CREATE TABLE `webagenda`.`user_extras`
(`userID`			VARCHAR(10));

CREATE TABLE `webagenda`.`working_jobs`
(`userID`			VARCHAR(10),
`jobID`				VARCHAR(10));

CREATE TABLE `webagenda`.`job_type`
(`jobID`			VARCHAR(10),
`jobName`			VARCHAR(50),
`jobDescription`	VARCHAR(250),
`mainSupervisor`	VARCHAR(10));