## Description

This project is a stock portfolio manager, where it is possible to manage multiple portfolios of a user.

## Technologies

The project was developed using the following technologies:

- [Kotlin](https://kotlinlang.org/) - Programming language
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for developing Java/Kotlin applications
- [PostgreSQL](https://www.postgresql.org/) - Database
- [Docker](https://www.docker.com/) - Tool for creating containers

## Running the project

To run the project, it is necessary to have Docker installed on the machine.
After installation, just run the command below at the root of the project:

```bash
docker compose up -d
```

## Project Structure

### Modules

The project was divided into modules:

- **account**: Contains the business rules of the users
- **wallet**: Contains the business rules of the wallets
- **stock**: Contains the business rules of the stocks
- **transaction**: Contains the business rules of the transactions
- **common**: Contains the classes common to all modules