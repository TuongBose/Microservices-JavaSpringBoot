# Microservices Java Spring Boot Project

## Project Structure

This repository contains a microservices architecture implemented with Java Spring Boot. The main components are:

- `api-gateway/` - Handles routing and API gateway logic.
- `discovery-server/` - Service discovery using Eureka or similar.
- `notification-service/` - Manages notifications.
- `order-serivce/` - Handles order management.
- `user-serivce/` - Manages user data and authentication.
- `database/` - Contains SQL scripts for initializing databases.
- `kafka-docker/` - Docker Compose setup for Kafka (message broker).

Each service is a standalone Spring Boot application with its own `pom.xml`, source code, and configuration files.

## How to Run

1. **Start Kafka (if used):**
   - Navigate to `kafka-docker/` and run:
     ```powershell
     docker-compose up -d
     ```

2. **Start Discovery Server:**
   - Navigate to `discovery-server/` and run:
     ```powershell
     .\mvnw spring-boot:run
     ```

3. **Start API Gateway:**
   - Navigate to `api-gateway/` and run:
     ```powershell
     .\mvnw spring-boot:run
     ```

4. **Start Microservices:**
   - For each service (`notification-service`, `order-serivce`, `user-serivce`), navigate to the folder and run:
     ```powershell
     .\mvnw spring-boot:run
     ```

5. **Database Setup:**
   - Use the SQL scripts in `database/` to initialize your databases as needed.

## Code Conventions

- **Java Naming:**
  - Classes: `PascalCase` (e.g., `OrderService`)
  - Methods/variables: `camelCase` (e.g., `getUserById`)
- **Spring Boot Structure:**
  - `src/main/java/` for source code
  - `src/main/resources/` for configuration and static files
- **Configuration:**
  - Use `application.yml` for environment-specific settings
- **REST API:**
  - Follow RESTful conventions for endpoints
  - Use DTOs for request/response bodies
- **Exception Handling:**
  - Use `@ControllerAdvice` for global exception handling
- **Logging:**
  - Use SLF4J (`LoggerFactory.getLogger`) for logging

## Additional Notes

- Ensure all services are registered with the discovery server.
- Update `application.yml` files for correct service URLs and ports.
- For development, run services locally; for production, consider Dockerizing each service.

---

Feel free to update this README with more details as your project evolves.
