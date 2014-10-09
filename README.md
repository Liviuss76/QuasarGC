QuasarGC
========

Quasar Game Center it's a self-hosted Java application designed to provide a backend service for Games.  
The main purpose of this application is to provide a solution for game developers who's target platform is Windows since Xbox API for gamecenter is not public.  
Quasar Game Center has ability to handle Player profiles, Leaderboards and Achievements trough a RESTful API.  
Quasar Game Center is built on top of spring boot application with embedded Tomcat 7 with thymeleaf front-end and PostgreSQL back-end in a self hosting .jar file built by maven.

###### Technologies
* Spring Boot + Security + MVC
* JPA + Hibernate
* Tomcat 7
* Thymeleaf
* Bootstrap
* PostgreSQL 9.3

###### Features
* Multiple Games
* Leaderboards
* Achievements
* Players profiles
* Players management
* Activity Logs
* Admin GUI  
* RESTful API
* Bruteforce protection
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
6. Generate a new keystore file for Tomcat SSL connector. Keytool utility is located in $JAVA_HOME/bin folder.
```
	$ keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
```  
Pay attention at "What is your first and last name?" question, here you need to enter qualified dns name of the server "ex: domain.com, ex: localhost", otherwise you will get errors on rest client.    
7. Copy "keystore.p12" file to the folder where executables reside (see p.4). The file provided in "Releases" folder is setup to work with "localhost" name.  
8. Update "application.properties" file, from the folder where executables reside (see p.4), and setup "keystore.pass" entry and "keystore.alias" entry if you changed alias for keystore.  
9. Start the server by executing  
```
	$ java -jar quasar-gamecenter-1.0.1-BUILD.jar
```
or it can be configured to run as service as explained [here](https://github.com/Liviuss76/QuasarGC/wiki/Running%20QuasarGC%20as%20Service%20on%20Linux)
10. Server should start and listen at port 8443/HTTPS. Admin control panel can be accessed at https://[YOUR IP HERE]:8443/  use username:admin password:admin to login.

Configuration  
======== 
All server configurations can be found in application.properties file.
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
	#Keystore Type PKCS12/JKS
	keystore.type: PKCS12
	#Keystore alias
	keystore.alias: tomcat
	#Number of failed login attepmts before timeblock
	login.retrybeforeblock: 3
	#block IP for N minutes after login.retrybeforeblock of failed logins 
	login.timetoblock : 2
```
  
REST Api  
======== 
##### http://YOUR SERVER/api/player  
Notes: Get Player profile  
RequestMethod: GET  
Security: Basic Auth  
Answer:
```
{
	"objectContainer":{
		"PlayerId":"PlayerId",
 	 	"PlayerUsername":"PlayerUsername",
 	 	"PlayerDisplayName":"PlayerDisplayName",
 	 	"PlayerFirstname":"PlayerFirstname",
 	 	"PlayerLastname":"PlayerLastname",
 	 	"PlayerEmail":"PlayerEmail",
 	 	"PlayerBirthdate":"PlayerBirthdate",
 	 	"PlayerSex":"PlayerSex",
 	 	"PlayerBlocked":False,
 	 	"PlayerPicture":"PlayerPicture"
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
  
  
##### http://YOUR SERVER/api/register  
Notes: Register Player  
RequestMethod: POST  
Security: Public access 
Post Object:
```
{
	"PlayerId":"PlayerId",
	"PlayerUsername":"PlayerUsername",
	"PlayerPassword":"PlayerPassword",
	"PlayerDisplayName":"PlayerDisplayName",
	"PlayerFirstname":"PlayerFirstname",
	"PlayerLastname":"PlayerLastname",
	"PlayerEmail":"PlayerEmail",
	"PlayerBirthdate":"PlayerBirthdate",
	"PlayerSex":"PlayerSex",
	"PlayerBlocked":False,
	"PlayerPlatform":"PlayerPlatform",
	"PlayerPicture":"PlayerPicture"
}
 ```
Answer:
```
{
	"objectContainer":{
		"PlayerId":"PlayerId",
 	 	"PlayerUsername":"PlayerUsername",
 	 	"PlayerDisplayName":"PlayerDisplayName",
 	 	"PlayerFirstname":"PlayerFirstname",
 	 	"PlayerLastname":"PlayerLastname",
 	 	"PlayerEmail":"PlayerEmail",
 	 	"PlayerBirthdate":"PlayerBirthdate",
 	 	"PlayerSex":"PlayerSex",
 	 	"PlayerBlocked":False,
 	 	"PlayerPicture":"PlayerPicture"
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
   
##### http://YOUR SERVER/api/update  
Notes: Update Player profile  
RequestMethod: POST  
Security: Basic Auth 
Post Object:
```
{
	"PlayerId":"PlayerId",
	"PlayerPassword":"PlayerPassword",
	"PlayerDisplayName":"PlayerDisplayName",
	"PlayerFirstname":"PlayerFirstname",
	"PlayerLastname":"PlayerLastname",
	"PlayerEmail":"PlayerEmail",
	"PlayerBirthdate":"PlayerBirthdate",
	"PlayerSex":"PlayerSex",
	"PlayerBlocked":False,
	"PlayerPlatform":"PlayerPlatform",
	"PlayerPicture":"PlayerPicture"
}
 ```
Answer:
```
{
	"objectContainer":{
		"PlayerId":"PlayerId",
 	 	"PlayerUsername":"PlayerUsername",
 	 	"PlayerDisplayName":"PlayerDisplayName",
 	 	"PlayerFirstname":"PlayerFirstname",
 	 	"PlayerLastname":"PlayerLastname",
 	 	"PlayerEmail":"PlayerEmail",
 	 	"PlayerBirthdate":"PlayerBirthdate",
 	 	"PlayerSex":"PlayerSex",
 	 	"PlayerBlocked":False,
 	 	"PlayerPicture":"PlayerPicture"
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
   
    
##### http://YOUR SERVER/api/scores  
Notes: Get all scores for a specific leaderboard  
RequestMethod: GET  
RequestParam: String "leaderboard" (mandatory)  
RequestParam: Integer "size" (optional)  
Security: Basic Auth  
Answer:
```
{
	"objectContainer":[{
		"playerUsername":"PlayerUsername",
 	 	"PlayerDisplayName":"PlayerDisplayName",
 	 	"PlayerScore":2219,
 	 	"PlayerRank":1,
 	 	"PlayerPlatform":"ANDROID",
 	 	"ScoresCount":2
	}],
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
  
  
##### http://YOUR SERVER/api/score  
Notes: Get Player score for a specific leaderboard  
RequestMethod: GET  
RequestParam: String "leaderboard"  (mandatory)  
Security: Basic Auth  
Answer:
```
{
	"objectContainer":{
		"playerUsername":"PlayerUsername",
		"PlayerDisplayName":"PlayerDisplayName",
		"PlayerScore":1219,
		"PlayerRank":2,
		"PlayerPlatform":"MAC",
		"ScoresCount":2
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
  
  
##### http://YOUR SERVER/api/score  
Notes: Submit Player score for a specific leaderboard  
RequestMethod: POST  
Security: Basic Auth  
Post Object:
```
{
	"LeaderboardId":"LeaderboardId",
	"PlayerScore":1219
}
 ``` 
Answer:  
```
{
	"objectContainer":{
		"playerUsername":"PlayerUsername",
		"PlayerDisplayName":"PlayerDisplayName",
		"PlayerScore":1219,
		"PlayerRank":2,
		"PlayerPlatform":"MAC",
		"ScoresCount":2
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```

##### http://YOUR SERVER/api/achievements 
Notes: Get all Player achievements for a specific Game
RequestMethod: GET  
RequestParam: String "game" (mandatory)  
Security: Basic Auth  
Answer:
```
{
	"objectContainer":[{
		"AchievementName":"AchievementName",
		"AchievementDesc":"AchievementDesc",
		"AchievementImage":"AchievementImage",
		"AchievementCode":"AchievementCode",
		"AchievementUnlockPoints":100.0,
		"AchievementBonusPoints":10,
		"AchievementProgress":90.0,
		"AchievementUnlocked":False,
		"AchievementRepeatable":False
	}],
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
  
##### http://YOUR SERVER/api/achievement
Notes: Get Player achievement
RequestMethod: GET  
RequestParam: String "code" (mandatory)  
Security: Basic Auth  
Answer:
```
{
	"objectContainer":{
		"AchievementName":"AchievementName",
		"AchievementDesc":"AchievementDesc",
		"AchievementImage":"AchievementImage",
		"AchievementCode":"AchievementCode",
		"AchievementUnlockPoints":100.0,
		"AchievementBonusPoints":10,
		"AchievementProgress":90.0,
		"AchievementUnlocked":False,
		"AchievementRepeatable":False
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```
  
##### http://YOUR SERVER/api/achievement
Notes: Submit Player achievement progress
RequestMethod: POST    
Security: Basic Auth  
Post Object:
```
{
	"AchievementCode":"AchievementCode",
	"AchievementUnlockPoints":11.0
}
 ``` 
Answer:
```
{
	"objectContainer":{
		"AchievementName":"AchievementName",
		"AchievementDesc":"AchievementDesc",
		"AchievementImage":"AchievementImage",
		"AchievementCode":"AchievementCode",
		"AchievementUnlockPoints":100.0,
		"AchievementBonusPoints":10,
		"AchievementProgress":90.0,
		"AchievementUnlocked":False,
		"AchievementRepeatable":False
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```  
  
    
##### http://YOUR SERVER/api/unlockachievement
Notes: Submit Player achievement progress
RequestMethod: POST    
Security: Basic Auth  
Post Object:
```
{
	"AchievementCode":"AchievementCode"
}
 ``` 
Answer:
```
{
	"objectContainer":{
		"AchievementName":"AchievementName",
		"AchievementDesc":"AchievementDesc",
		"AchievementImage":"AchievementImage",
		"AchievementCode":"AchievementCode",
		"AchievementUnlockPoints":100.0,
		"AchievementBonusPoints":10,
		"AchievementProgress":90.0,
		"AchievementUnlocked":False,
		"AchievementRepeatable":False
	},
	"ErrorInfo":{
		"ErrorMessage":"ErrorMessage"
	}
}
 ```  
  
  
Build from source 
========
###### Build with Maven

```
	$ mvn clean package
```