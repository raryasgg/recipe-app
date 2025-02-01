# Recipe API

A Spring Boot application to manage recipes with features like loading, searching, and retrieving recipes.

## Features
- Load recipes from a dataset.
- Search recipes by name or cuisine.
- Retrieve recipes by ID.

## Prerequisites
- Java 17+
- Maven 3.8+

## Running the Application

1. Build the application:
mvn clean install
2. Run the application:
mvn spring-boot:run
3. Access the Swagger UI:
http://localhost:8080/swagger-ui.html

## API Endpoints
- `POST /api/recipes/load`: Load recipes from the dataset.
- `GET /api/recipes/search?query=<search-query>`: Search recipes.
- `GET /api/recipes/{id}`: Retrieve a recipe by ID.


