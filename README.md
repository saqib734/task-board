# Problem Statement

This project creates a simple REST service for a task board that allows a user to create and manage
different lists of tasks which are persisted between devices. The application should
implement the following user stories:

1. As a user, I can view all lists and tasks which have been created
2. As a user, I can create an empty list with a name property
3. As a user, I can add new tasks to an existing list with a name and description property.
4. As a user, I can update the name and description of a tasks within a list
5. As a user, I can delete a task from a list
6. As a user, I can delete an entire list with all its tasks
7. As a user, I can move tasks to a different list

## How to run the application

JDK 17 is required to run this application. The following sections list the steps to run the application with or without Docker.

#### Using Docker
Application can be run using docker. The following steps can be used to run the application

- Build the application
```shell
  ./gradlew build
```

- Build the docker image
```shell
  docker build -t taskboard-api .
```  

- Run the docker container. It exposed the APIs on port 9080.
```shell
  docker run -p 9080:8080 taskboard-api
```

#### Using java (Host system)
In case docker is not installed on the host system, application can be run on host system using below steps. Java should be installed

- Build the application
```shell
  ./gradlew build
```
- Run the application. It exposes the APIs on port 9080.
```shell
  java -jar build/libs/task-board-0.0.1-SNAPSHOT.jar --server.port=9080
```

## Sample API requests

#### Create a list

```shell
curl --location 'http://localhost:9080/api/v1/lists' \
--header 'Content-Type: application/json' \
--data '{
    "name": "ToDo-Apr-22"
}'
```

#### Add a task to a list

```shell
curl --location 'http://localhost:9080/api/v1/lists/1/tasks' \
--header 'Content-Type: application/json' \
--data '{
"name": "Task 2",
"description": "Task 2 should be done before 09:00 on April 22"
}'
```

#### Get all lists with their tasks
This API returns all lists and tasks that have been created. It is generally not a good idea to return
an unbounded list in API response, and it is best to paginate the API.
```shell
curl --location --request GET 'http://localhost:9080/api/v1/lists' 
```

#### Update a task

```shell
curl --location --request PUT 'http://localhost:9080/api/v1/lists/tasks/1' \
--header 'Content-Type: application/json' \
--data '{
"name": "Task 2",
"description": "Task 2 should be done after 10:00 on April 22"
}'
```

#### Move a task to a different list

```shell
curl --location --request POST 'http://localhost:9080/api/v1/lists/tasks/2/move/1' 
```

#### Delete a list

```shell
curl --location --request DELETE 'http://localhost:9080/api/v1/lists/1' 
```

#### Delete a task
```shell
curl --location --request DELETE 'http://localhost:9080/api/v1/lists/tasks/1' 
```

## Design Considerations

#### Test coverage and types of tests

The project contains an integration test and unit tests for controller and service layer.

#### Architectural design choices

- The project is built using Spring Boot 3 with Java 17. 
- Gradle is used to build the application
- JPA repositories are used for persistence layer.
- For the sake of simplicity, H2 (in-memory database) is used
- Code is divided into typical controller/service/repository layers
- APIs use Jakarta bean validation for basic input validation

#### How to build and run the application

- Some instructions have been provided about building / running the application in earlier sections

#### Relational or non-relational database

- Since both TaskList & Task are structured entities with defined columns, I decided to use SQL database.