/* create user / grant permissions */
/* GO TO C:\Program Files\MySQL\MySQL Server 5.7\bin */

DROP DATABASE IF EXISTS cst8276project;
DROP USER IF EXISTS 'cst8276project'@'localhost';

create user 'cst8276project'@'localhost' identified by 'password';
create database cst8276project;
grant select, insert, update, delete, create, drop, 
references, execute, alter on cst8276project.* to 'cst8276project'@'localhost';

/* create db / table */
USE cst8276project;
CREATE TABLE SampleDataLevelOne(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier int  NOT NULL
);
CREATE TABLE SampleDataLevelTwo(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	alpha int  NOT NULL,
	beta int NOT NULL,
	gamma int NOT NULL,
	delta int NOT NULL,
	theta int NOT NULL
);
CREATE TABLE SampleDataLevelThree(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	emp_birthDate int  NOT NULL,
	emp_fname int NOT NULL,
	emp_lname int NOT NULL,
	emp_gender int NOT NULL,
	emp_hireDate int NOT NULL
);
CREATE TABLE Salary(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	sal_empNum int  NOT NULL,
	sal_fromDate int NOT NULL,
	sal_toDate int NOT NULL,
	sal_salary int NOT NULL
);
CREATE TABLE Title(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ttl_empNum int  NOT NULL,
	ttl_title int NOT NULL,
	ttl_fromDate int NOT NULL,
	ttl_toDate int NOT NULL
);




