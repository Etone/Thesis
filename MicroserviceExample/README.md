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

### Docker
- Installation via apt-get unusual
  - adding own repository, https and pgp key
  - Good documentation helps a lot
- Usage
  - relatively simple, Writing small Dockerfiles
  - portbinding and networksettings bit confusing (Host -> VM -> Docker Container)

#### Steps
1. Write Dockerfiles (already there in each Folder)
2. ```docker build -t <TAG> (--build-arg JAR_FILE=./target/todos-backend-0.0.1-SNAPSHOT.jar) .```
3. ```docker run -d (-e POSTGRES_IP=<IP container data> -e POSTGRES_PORT=5432> [-p 80:80] (-p 8080:8080) <TAG>```
  - Note running Containers in Order: todos:data &rarr; todos:backend &rarr; todos:front

() only needed for Backend
[] only needed for Frontend

It takes me around **6 Minutes** the first time running this, with no cached images  
All 3 Images together are **109Mb(front) + 676Mb(back) + 289Mb(data) = 1,074Gb** in size.

#### Docker Compose
Using Docker Compose instead of three images makes the whole Process even easier.
```yaml
version: '3'
services:
  data:
    image: library/postgres
    environment:
      POSTGRES_USER: docker
      POSTGRES_PASSWORD: docker
      POSTGRES_DB: todos
  back:
    build:
      context: ./backend
    ports:
      - "8080:8080"
    environment:
      POSTGRES_PORT: 5432
  front:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./frontend:/usr/share/nginx/html

```
