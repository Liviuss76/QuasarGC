QuasarGC
========

Quasar Game Center it's a self-hosted Java application designed to provide a backend service for Games in order to host Leaderboards, Achievements and Player profiles. 
Quasar Game Center use spring boot application with embedded Tomcat 7 with thymeleaf frontend and postgresql backend in a self hosting .jar file built by maven.

###### Technologies
* Spring Boot + Security + MVC
* JPA + Hibernate
* Tomcat 7
* Thymeleaf
* Bootstrap
* PostgreSQL 9.3

###### Features
* Leaderboards
* Achievements
* Players profiles
* Players management
* Activity Logs
* Admin GUI  
* RESTful API
* Unity3d Plugin

Installation and Getting Started  
========  
1. Install PostgreSQL server from [http://www.postgresql.org/download/](http://www.postgresql.org/download/)  
On Linux you can use Yum [https://wiki.postgresql.org/wiki/YUM_Installation](https://wiki.postgresql.org/wiki/YUM_Installation) or Apt [https://wiki.postgresql.org/wiki/Apt](https://wiki.postgresql.org/wiki/Apt)  
2. Install JavaSE 1.6 or JavaSE 1.7 from [http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  
3. Get Database schema from folder "Releases". From console execute:  
```
	$ su postgres
```
```
	$ psql < quasargc_1.0.1.sql
```
After execution you will get a fully functional database named "quasargc" with a default user "admin" pass "admin"  
4. Create a folder for server executables and config files  
5. Copy files from folder "Releases" into newly created folder  
6. Start the server by executing  
```
	$ java -jar quasar-gamecenter-1.0.1-BUILD.jar
```
Server should start and listen at port 8443/HTTPS. Admin control panel can be accessed at https://[YOUR IP HERE]:8443/  

Configuration  
======== 
All server configurations can be found in side the application.properties file.
```
	#Database URL
	database.url: jdbc:postgresql://localhost/quasargc
	#Database Username
	database.username: postgres
	#Database Password
	database.password: postgres
	#Print Generated SQLs in console
	database.debugsql: false
	#HTTPS server port
	server.port: 8443
	#Keystore file
	keystore.file: keystore.p12
	#Keystore Password
	keystore.pass: localhost
	#Keystore Type
	keystore.type: PKCS12
	#Keystore alias
	keystore.alias: tomcat
	#Number of failed login attepmts before timeblock
	login.retrybeforeblock: 3
	#block IP for N minutes after login.retrybeforeblock of failed logins 
	login.timetoblock : 2
```


Build from source 
========
###### Build with Maven

```
	$ mvn clean package
```