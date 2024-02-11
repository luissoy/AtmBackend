# AtmBackend API

The development revolves around the creation of an API that facilitates connectivity between ATMs and banks. The project focuses exclusively on backend development. The primary objective is to streamline and increase the efficiency of ATM operations, ensuring seamless interactions. The API will enable users to access transaction details, withdraw and deposit funds, transfer funds, activate cards and change PINs.

## Project Structure

- **Name:** AtmBackend
- **Framework:** Spring Boot
- **Version:** 3.2.2
- **Java Version:** 17

## OpenAPI Definition

Swagger: http://localhost:8080/swagger

ApiDocs: http://localhost:8080/api-docs
```json
{
  "openapi": "3.0.1",
  "info": {
    "title": "OpenAPI definition",
    "version": "v0"
  },
  "servers": [
    {
      "url": "http://localhost:8080",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/api/v1/users": {
      // ... User operations
    },
    "/api/v1/banks": {
      // ... Bank operations
    },
    "/api/v1/accounts/{userId}/{bankId}": {
      // ... Account operations
    },
    "/api/v1/accounts": {
      // ... Get accounts operation
    }
  }
}
```
## Docker Configuration

Docker Configuration
The provided docker-compose file facilitates environment setup by deploying MySQL (version 8.3) and Adminer services, along with a Dockerfile for the Spring Boot application. Utilize "docker-compose up" to initiate the environment.