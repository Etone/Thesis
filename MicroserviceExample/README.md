# Todos - Micro service architecture example
This is an example for a microservice oriented architecture.
## Components
It uses three components
1. frontend  
index.html - uses axios (for REST Requests), VueJS (for data manipulation) and
bootstrap (as UI framework)
2. backend  
Spring Boot Application with model and RESTful server giving access to Database
3. data  
containing Dockerfile that uses postgres.
Will in future contain aci and other Image formats

## Starting the Application
1. Start the docker container build with the given [Dockerfile](./data/Dockerfile)
2. package and run spring application (**THIS USES JAVA9**)
```bash
./backend/mvnw package
java - jar ./backend/target/todos-backend-0.0.1-SNAPSHOT.jar
```
3. Either doubleclick [index.html](./frontend/index.html) or serve it via Webserver

## What is this used for
As most examples used Docker to show how an app can be containerized,
I wanted to implement a service-oriented app that uses different technologies
to try out different Container-Runtimes. That's how this app was created.

To try out how different Runtimes work, this will be containered with
1. Docker (ofc)
2. rkt (as the biggest competitor)
3. runC (standardized runtime)
4. ... (not decided what I want to check)

This will show how the runtimes handle a pretty basic workload with
Database -> Java Backend -> JS Frontend
