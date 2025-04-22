# Waste Sorting API

## Overview

This project is a Waste Management System API that provides endpoints for managing waste categories, disposal guidelines, and recycling tips. The API is deployed on Render and uses MongoDB Atlas for data storage.

## Technologies Used

- Java 17
- Spring Boot
- MongoDB Atlas (primary database)
- Embedded MongoDB (fallback for development/testing)
- Spring Security with JWT Authentication
- Maven
- Swagger/OpenAPI for documentation

## API Endpoints

### Waste Categories

- **GET /api/waste-categories**: Get all waste categories.
- **GET /api/waste-categories/paged**: Get all waste categories with pagination and sorting.
- **GET /api/waste-categories/{id}**: Get a waste category by ID.
- **POST /api/waste-categories**: Create a new waste category.
- **PUT /api/waste-categories/{id}**: Update an existing waste category.
- **PATCH /api/waste-categories/{id}**: Partially update an existing waste category.
- **DELETE /api/waste-categories/{id}**: Delete a waste category by ID.
- **GET /api/waste-categories/search**: Search waste categories with optional parameters (name, description, recyclable, disposalMethod).
- **GET /api/waste-categories/search/paged**: Search waste categories with pagination and sorting.
- **GET /api/waste-categories/search/name**: Find waste categories by name.
- **GET /api/waste-categories/search/description**: Find waste categories by description.
- **GET /api/waste-categories/search/recyclable**: Find waste categories by recyclable flag.

### Disposal Guidelines

- **GET /api/disposal-guidelines**: Get all disposal guidelines.
- **GET /api/disposal-guidelines/{id}**: Get a disposal guideline by ID.
- **POST /api/disposal-guidelines**: Create a new disposal guideline.
- **PUT /api/disposal-guidelines/{id}**: Update an existing disposal guideline.
- **PATCH /api/disposal-guidelines/{id}**: Partially update an existing disposal guideline.
- **DELETE /api/disposal-guidelines/{id}**: Delete a disposal guideline by ID.
- **GET /api/disposal-guidelines/search**: Search disposal guidelines with optional parameters (wasteCategoryId, description, steps, legalRequirements).
- **GET /api/disposal-guidelines/search/waste-category/{wasteCategoryId}**: Find disposal guidelines by waste category ID.
- **GET /api/disposal-guidelines/search/description**: Find disposal guidelines by description.
- **GET /api/disposal-guidelines/search/steps**: Find disposal guidelines by steps.
- **GET /api/disposal-guidelines/search/legal-requirements**: Find disposal guidelines by legal requirements.

### Recycling Tips

- **GET /api/recycling-tips**: Get all recycling tips.
- **GET /api/recycling-tips/{id}**: Get a recycling tip by ID.
- **POST /api/recycling-tips**: Create a new recycling tip.
- **PUT /api/recycling-tips/{id}**: Update an existing recycling tip.
- **PATCH /api/recycling-tips/{id}**: Partially update an existing recycling tip.
- **DELETE /api/recycling-tips/{id}**: Delete a recycling tip by ID.
- **GET /api/recycling-tips/search**: Search recycling tips with optional parameters (wasteCategoryId, title, description, source, recyclableOnly).
- **GET /api/recycling-tips/search/waste-category/{wasteCategoryId}**: Find recycling tips by waste category ID.
- **GET /api/recycling-tips/search/title**: Find recycling tips by title.
- **GET /api/recycling-tips/search/description**: Find recycling tips by description.
- **GET /api/recycling-tips/search/source**: Find recycling tips by source.

### Waste Collections

- **GET /api/waste-collections**: Get all waste collections.
- **GET /api/waste-collections/paged**: Get all waste collections with pagination and sorting.
- **GET /api/waste-collections/{id}**: Get a waste collection by ID.
- **POST /api/waste-collections**: Create a new waste collection.
- **PUT /api/waste-collections/{id}**: Update an existing waste collection.
- **PATCH /api/waste-collections/{id}**: Partially update an existing waste collection.
- **DELETE /api/waste-collections/{id}**: Delete a waste collection by ID.
- **GET /api/waste-collections/search**: Search waste collections with optional parameters (wasteCategoryId, location, status, startDate, endDate).
- **GET /api/waste-collections/search/paged**: Search waste collections with pagination and sorting.
- **GET /api/waste-collections/search/waste-category/{wasteCategoryId}**: Find waste collections by waste category ID.
- **GET /api/waste-collections/search/location**: Find waste collections by location.
- **GET /api/waste-collections/search/status**: Find waste collections by status.
- **GET /api/waste-collections/search/date-range**: Find waste collections by date range.

### Recycling Centers

- **GET /api/recycling-centers**: Get all recycling centers.
- **GET /api/recycling-centers/paged**: Get all recycling centers with pagination and sorting.
- **GET /api/recycling-centers/{id}**: Get a recycling center by ID.
- **POST /api/recycling-centers**: Create a new recycling center.
- **PUT /api/recycling-centers/{id}**: Update an existing recycling center.
- **PATCH /api/recycling-centers/{id}**: Partially update an existing recycling center.
- **DELETE /api/recycling-centers/{id}**: Delete a recycling center by ID.
- **GET /api/recycling-centers/search**: Search recycling centers with optional parameters (name, address, active).
- **GET /api/recycling-centers/search/paged**: Search recycling centers with pagination and sorting.
- **GET /api/recycling-centers/search/waste-category/{wasteCategoryId}**: Find recycling centers by accepted waste category ID.
- **GET /api/recycling-centers/search/name**: Find recycling centers by name.
- **GET /api/recycling-centers/search/address**: Find recycling centers by address.
- **GET /api/recycling-centers/search/active**: Find recycling centers by active status.

### Reports

- **GET /api/reports/by-category**: Get waste collections report grouped by category.
- **GET /api/reports/by-location**: Get waste collections report grouped by location.
- **GET /api/reports/by-date-range**: Get waste collections report within a date range.
- **GET /api/reports/recyclable-vs-non-recyclable**: Get report comparing recyclable and non-recyclable waste.
- **GET /api/reports/by-status**: Get waste collections report grouped by status.
- **GET /api/reports/comprehensive**: Get a comprehensive report with all metrics.

## Deployment

The API is deployed and accessible at: https://waste-sorting-api.onrender.com

## Running the Application Locally

### Option 1: Using MongoDB Atlas (Recommended)

1. Clone the repository: `git clone https://github.com/SeanXIV/Waste-sorting-api.git`
2. Navigate to the project directory: `cd Waste-sorting-api`
3. Create a `.env` file in the root directory with your MongoDB Atlas connection string:
   ```
   MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/waste_management?retryWrites=true&w=majority
   ```
4. Build the project: `mvn clean install`
5. Run the application: `java -jar ./target/andrewseanego-0.0.1-SNAPSHOT.jar`

### Option 2: Using Embedded MongoDB

1. Clone the repository: `git clone https://github.com/SeanXIV/Waste-sorting-api.git`
2. Navigate to the project directory: `cd Waste-sorting-api`
3. Build the project: `mvn clean install`
4. Run the application: `java -jar ./target/andrewseanego-0.0.1-SNAPSHOT.jar`

The application will automatically fall back to embedded MongoDB if it cannot connect to MongoDB Atlas.

## API Documentation

The API documentation is available via Swagger UI:

- **Deployed Swagger UI**: [https://waste-sorting-api.onrender.com/swagger-ui.html](https://waste-sorting-api.onrender.com/swagger-ui.html)
- **Deployed OpenAPI Docs**: [https://waste-sorting-api.onrender.com/api-docs](https://waste-sorting-api.onrender.com/api-docs)
- **Local Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Local OpenAPI Docs**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Authentication

The API uses JWT (JSON Web Token) for authentication. To access protected endpoints, you need to:

1. Register a user: `POST /api/auth/signup`
2. Login to get a token: `POST /api/auth/signin`
3. Include the token in the Authorization header: `Bearer {token}`

### Authentication Endpoints

- **POST /api/auth/signup**: Register a new user
  - Request body: `{"username": "user", "email": "user@example.com", "password": "password", "role": ["user"]}`
  - Possible roles: `user`, `mod`, `admin`

- **POST /api/auth/signin**: Login and get a token
  - Request body: `{"username": "user", "password": "password"}`
  - Response includes the JWT token to use for authentication


## Frontend Application

A separate frontend application is being developed to interact with this API. The frontend will be built using Next.js and deployed on Vercel.

## Contributors

- Andrew Seanego <https://www.linkedin.com/in/andrew-seanego-1a4013280/>
