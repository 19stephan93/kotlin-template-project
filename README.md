# Kotlin Template Project
A Kotlin and Spring Boot walking skeleton that can be used as a starting point in developing a full application.

## Contents
* [Database migration](#db)  
* [Testing](#testing)  
* [Allure Test Reports](#allure)

<a name="db"></a>
## Database migration 
This skeleton is using a Postgresql database that runs in a Docker container.  
The tool that is used for database migration is Liquibase. It uses a `changelog` file that lists the database changes in order.   

To start the database, we use an existing Docker image from DockerHub - `postgres:13.2`:
```cmd
cd docker/db
docker-compose up -d
```
Check if the container is running:
```cmd
docker ps
```
In order to migrate the database schema run the following command:
```cmd
cd ../..
docker run --rm --network host -v ~/<PATH_TO_PROJECT>/kotlin-template-project/liquibase:/liquibase/changelog liquibase/liquibase:4.3.5 --defaultsFile=/liquibase/changelog/liquibase.docker.properties --changeLogFile=changelog.xml update
```
The previous command starts the Liquibase Docker container from an existing Docker image on DockerHub `liquibase/liquibase:4.3.5` and migrates the database according to the `liquibase/changelog.xml` file.
<a name="testing"></a>

## Testing

### Unit testing
Run unit tests:
```cmd
gradle unit-tests
```

### Integration testing 
Run integration testing (make sure that the application is running):
```cmd
gradle integration-tests
```
<a name="allure"></a>
## Allure Test Reports
Upload testing results to `Allure Server` <br><br>
```./scripts/upload-tests-results.sh "http://allure-server-nlb-5aca28bde8fd1774.elb.eu-central-1.amazonaws.com" user pass "kotlin-template-project-stefan-laptop" >/dev/null 2>&1```<br><br>
Visit to login ```http://allure-server-nlb-5aca28bde8fd1774.elb.eu-central-1.amazonaws.com``` <br> <br>

Visit
http://allure-server-nlb-5aca28bde8fd1774.elb.eu-central-1.amazonaws.com/allure-docker-service/projects/kotlin-template-project-stefan-laptop/reports/latest/index.html

