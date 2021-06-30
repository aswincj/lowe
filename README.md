# lowe
As part of the interview I have created a url shortner Using Spring Boot and angular 8

The spring boot application (lowe-api) is used to generate short url and divert the short url into the base url.

The angular application provides an interface to user to generate a short url and view the short urls created by the application and it also indicates how much many times we have used a specific url

We are using Mysql database to store the details

#Installation Guide
1. Install mysql workbench from https://dev.mysql.com/downloads/workbench/ 
2. set username and password as root and 123 respectively 
3. run the below commands in the workbench


  CREATE DATABASE `shorturl`

  CREATE TABLE `shorturl`.`url_table` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(100) NULL,
    `baseUrl` VARCHAR(100) NULL,
    `hits` INT NULL,
    PRIMARY KEY (`id`)) 
    
4. copy lowe/lowe-api to your local
5. copy lowe/angular to your local
6. open lowe-api folder in cmd and do "mvn clean install"; It will download all the dependencies
7. run lowe-api as java application in eclipse
8. open angular folder in cmd and enter the below commands one by one
 
    npm install -g @angular/cli
    
    npm install
    
    ng serve
    
The sping boot application will run in http://localhost:8080/ and angular application will run in http://localhost:4200/
  

