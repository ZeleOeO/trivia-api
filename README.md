# Trivia API

--- 
Implementation of the [OpenTrivia](https://opentdb.com/) api to make a quiz app in Java Spring Boot.

## Technologies

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-blue?logo=postgresql&logoColor=white)

## Features
- Start a quiz
- Submit a quiz
- Get your quiz results
- Access quizzes via their quiz ids

## Prerequisites

- Java 17+
- Docker
- **Optional** - API Client (Postman, Yaak, Insominia etc.). You can always use curl
- **Optional** - Database Management Tool (Dbeaver, Navicat, DataGrip etc.) I think some IDE's come inbuilt with them
- **Optional** - An IDE such as IntelliJ, VSCode, Netbeans

## Installation (With Git)

1. Clone the repository:
   ```bash
   git clone https://github.com/ZeleOeO/trivia-api.git
   ```
2. Navigate to the project directory:
   ```bash
   cd trivia-api
   ```   
3. Open docker if not already running (spring-boot-docker-compose is installed so no need for docker-compose)

## Installation (With Docker)
1. Run the following
    ```shell
   docker pull ozonichi/trivia-api
   ```


## Running
There are 2 ways to run this project by either using:
<br>
- Complete Docker Compose:
```bash
docker-compose up --build
```
Or,
<br>
Composing just the db individually on docker compose and then running the app natively
```shell
docker-compose up --build
mvn spring-boot:run
```

The db is hosted on docker, cause that's easier to set up than having pgadmin

## Usage

- Database
    - To connect and check the database, it is hosted on `jdbc:postgresql://localhost:5432/trivia-db`
    - username: `user`
    - password: `password`


- Application
    - port : `8080`


- API Endpoints
  <br>
  You can access the docs by checking:
    - Markdown doc at [API-DOC](docs/api-docs.md)
    - [Postman Collection](docs/Trivia%20App.postman_collection.json) _(Didn't know you still had to freaking sign up to use
      ts, kinda annoying)_
    - There's also [swagger-ui](http://localhost:8080/swagger-ui/index.html) for when the application is running

## Testing

For now, don't have tests, but if i did

```shell
mvn test
```

## Steps to Contribute

Contributions are more than welcome, I'm still working on it though, so... keep that in mind or something.

1. Open an issue first so I can like keep track, but if that's too much stress that's fine too
2. Fork the Repository
3. Clone your fork
4. Create a new branch
   ```bash
   git checkout -b your-branch-name
   ```
5. Make your change
6. Commit your change, please
   use [Conventional Commits](https://gist.github.com/qoomon/5dfcdf8eec66a051ecd85625518cfd13) if you can, I didn't
   really use it here tbh
7. Push your change
8. Make a pull request and reference your issue <br>
   Please stick to conventional methods of programming java and springboot applications, don't mess up my already
   spaghetti code