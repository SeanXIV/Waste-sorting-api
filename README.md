# Enviro365-Assessment

## Overview

This project is a Waste Management System API that provides endpoints for managing waste categories, disposal guidelines, and recycling tips.

## Technologies Used

- Java 17
- Spring Boot
- H2 Database
- Maven

## API Endpoints

### Waste Categories

- **GET /api/waste-categories**: Get all waste categories.
- **GET /api/waste-categories/{id}**: Get a waste category by ID.
- **POST /api/waste-categories**: Create a new waste category.
- **DELETE /api/waste-categories/{id}**: Delete a waste category by ID.

### Disposal Guidelines

- **GET /api/disposal-guidelines**: Get all disposal guidelines.
- **GET /api/disposal-guidelines/{id}**: Get a disposal guideline by ID.
- **POST /api/disposal-guidelines**: Create a new disposal guideline.
- **DELETE /api/disposal-guidelines/{id}**: Delete a disposal guideline by ID.

### Recycling Tips

- **GET /api/recycling-tips**: Get all recycling tips.
- **GET /api/recycling-tips/{id}**: Get a recycling tip by ID.
- **POST /api/recycling-tips**: Create a new recycling tip.
- **DELETE /api/recycling-tips/{id}**: Delete a recycling tip by ID.

## Running the Application

1. Clone the repository: `git clone https://github.com/SeanXIV/Enviro365-Assessment.git`
2. Navigate to the project directory: `cd Enviro365-Assessment`
3. Build the project: `mvn clean install`
4. Run the application: `java -jar .\target\andrewseanego-0.0.1-SNAPSHOT.jar`


## Contributors

- Andrew Seanego <https://www.linkedin.com/in/andrew-seanego-1a4013280/>
