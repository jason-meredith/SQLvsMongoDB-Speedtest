/* create user / grant permissions */
/* GO TO C:\Program Files\MySQL\MySQL Server 5.7\bin */
create user 'cst8276project'@'localhost' identified by 'password';
create database cst8276project;
grant select, insert, update, delete, create, drop, 
references, execute, alter on cst8276project.* to 'cst8276project'@'localhost';

/* create db / table */
DROP DATABASE IF EXISTS cst8276project;
DROP TABLE IF EXISTS SampleDataLevelOne;
DROP TABLE IF EXISTS SampleDataLevelTwo;
DROP TABLE IF EXISTS SampleDataLevelThree;
DROP TABLE IF EXISTS Salary;
DROP TABLE IF EXISTS Title;
CREATE DATABASE cst8276project;
USE cst8276project;
CREATE TABLE SampleDataLevelOne(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier int  NOT NULL
);
CREATE TABLE SampleDataLevelTwo(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier int  NOT NULL,
	alpha VARCHAR(42)  NOT NULL,
	beta VARCHAR(42) NOT NULL,
	gamma VARCHAR(42) NOT NULL,
	delta VARCHAR(42) NOT NULL,
	theta VARCHAR(42) NOT NULL
);
CREATE TABLE SampleDataLevelThree(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier VARCHAR(42)  NOT NULL,
	emp_birthDate VARCHAR(42)  NOT NULL,
	emp_fname VARCHAR(42) NOT NULL,
	emp_lname VARCHAR(42) NOT NULL,
	emp_gender VARCHAR(42) NOT NULL,
	emp_hireDate VARCHAR(42) NOT NULL
);
CREATE TABLE Salary(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier VARCHAR(42)  NOT NULL,
	sal_empNum VARCHAR(42)  NOT NULL,
	sal_fromDate VARCHAR(42) NOT NULL,
	sal_toDate VARCHAR(42) NOT NULL,
	sal_salary VARCHAR(42) NOT NULL
);
CREATE TABLE Title(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier VARCHAR(42)  NOT NULL,
	ttl_empNum VARCHAR(42)  NOT NULL,
	ttl_title VARCHAR(42) NOT NULL,
	ttl_fromDate VARCHAR(42) NOT NULL,
	ttl_toDate VARCHAR(42) NOT NULL
);




