DCB system and Operator

Required tool: Docker, Docker compose.

I) Step to build system as below:

Step 1: Start database:
- Go to DCB folder and implement statement as below:
	docker-compose -f docker-compose.mysql.yml up -d

- Go to Operator folder and implement statement as below:
	docker-compose -f docker-compose_postgresql.yml up -d

Step 2: Start project DCB
go to DCB project in folder DCB/source and run gradle build by statement
	gradlew clean build -x test
Waiting for build success and run project by statement as below:
	java -jar build\libs\DCB-0.0.1-SNAPSHOT.war


Step 3: Start project DCB
go to Operator project in folder Operator/source and run gradle build by statement
	gradlew clean build -x test
Waiting for build success and run project by statement as below:
	java -jar build\libs\Operator-0.0.1-SNAPSHOT.war


II) Test system.