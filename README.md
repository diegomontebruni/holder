## Description

This project is a stock portfolio manager, where it is possible to manage multiple portfolios of a user.

## Technologies

The project was developed using the following technologies:

- [Kotlin](https://kotlinlang.org/) - Programming language
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for developing Java/Kotlin applications
- [PostgreSQL](https://www.postgresql.org/) - Database
- [Docker](https://www.docker.com/) - Tool for creating containers

## Project Structure

### Modules

The project was divided into modules:

- **account**: Contains the business rules of the users
- **wallet**: Contains the business rules of the wallets
- **stock**: Contains the business rules of the stocks
- **transaction**: Contains the business rules of the transactions
- **common**: Contains the classes common to all modules

### Software Design

The project was developed using a mix of Clean Architecture and Port and Adapters

## Running the project

To run the project, Docker must be installed on the machine to start the dependencies.
After installation, simply execute the command below at the root of the project to start the dependencies:

```bash
docker compose up -d
```

After the dependencies are up and running, the project can be run using the Gradle Wrapper. 
Java must be installed on your machine for this. 
After installation, you can execute the following command at the root of the project:

```bash
./gradlew bootRun
```

## API Documentation

This project uses Swagger for API documentation. You can access the Swagger UI by navigating to the `/swagger-ui.html` endpoint of the running application. For example, if the application is running locally on port 8080, you can access the Swagger UI at `http://localhost:8080/swagger-ui.html`.

