# Microservices Java Spring Boot Project

This repository contains a microservices-based application built with Java Spring Boot, a Go-based inventory service, and supporting infrastructure such as MySQL, Kafka, Redis, Keycloak, Prometheus, and Grafana.

## 1. Project structure

```text
.
├── api-gateway/                # Spring Cloud Gateway entry point
├── discovery-server/           # Eureka discovery server
├── notification-service/       # Spring Boot notification service
├── order-serivce/              # Spring Boot order service
├── payment-service/            # Spring Boot payment service
├── user-serivce/               # Spring Boot user service
├── inventory-service-golang/   # Go inventory service
├── database/                   # SQL scripts for MySQL databases
├── docker/                     # Docker Compose and monitoring config
├── demo-realm.json             # Keycloak realm export example
├── Howtorun.txt                # Additional local run notes
└── README.md                   # Project documentation
```

### Service responsibilities

- `api-gateway`: routes requests to downstream services and uses Eureka for service discovery.
- `discovery-server`: registers all Spring Boot services and allows service lookup.
- `user-serivce`: manages user-related APIs and uses Redis for caching.
- `order-serivce`: manages order processing and publishes/consumes Kafka events.
- `notification-service`: listens to events and sends notifications.
- `payment-service`: handles payment-related flows.
- `inventory-service-golang`: provides inventory operations in Go.

## 2. Prerequisites

Make sure the following tools are installed:

- Java 17+
- Maven (or use the Maven wrapper in each Spring Boot service)
- Go 1.24.5+ for the inventory service
- Docker Desktop / Docker Engine
- MySQL, Redis, Kafka, and Keycloak are provided by Docker Compose in this project

## 3. Start infrastructure services

From the project root, run:

```powershell
cd docker
docker compose up -d
```

This starts:

- MySQL on port `3306`
- phpMyAdmin on port `8100`
- Kafka on port `9092`
- Redis on port `6379`
- Keycloak on port `8080`
- Prometheus on port `9090`
- Grafana on port `3000`

If you prefer the legacy command, you can also use `docker-compose up -d`.

### Database setup

Import the SQL files from the `database/` folder into MySQL:

- `userdb.sql`
- `orderdb.sql`
- `inventories.sql`
- `reserved_orders.sql`

If Keycloak is used for authentication, import `demo-realm.json` into the local Keycloak instance.

## 4. How to run the services

Start the services in this order:

1. Discovery server
2. API gateway
3. Other microservices

### 4.1 Discovery server

```powershell
cd discovery-server
./mvnw spring-boot:run
```

### 4.2 API gateway

```powershell
cd api-gateway
./mvnw spring-boot:run
```

### 4.3 User service

```powershell
cd user-serivce
./mvnw spring-boot:run
```

### 4.4 Order service

```powershell
cd order-serivce
./mvnw spring-boot:run
```

### 4.5 Notification service

```powershell
cd notification-service
./mvnw spring-boot:run
```

### 4.6 Payment service

```powershell
cd payment-service
./mvnw spring-boot:run
```

### 4.7 Inventory service (Go)

```powershell
cd inventory-service-golang
go mod tidy
go run .
```

## 5. Default ports

- Discovery server: `8761`
- API gateway: `8084`
- User service: `8081`
- Order service: `8082`
- Notification service: `8083`
- Keycloak: `8080`
- Prometheus: `9090`
- Grafana: `3000`
- phpMyAdmin: `8100`
- gRPC user service: `9090`

## 6. Code conventions

Follow these conventions across the Java services:

- Use English names for packages, classes, methods, and variables.
- Class names: `PascalCase` (for example, `OrderService`).
- Methods and variables: `camelCase` (for example, `getOrderById`).
- Constants: `UPPER_SNAKE_CASE`.
- Package names: lowercase, usually grouped by domain, for example `com.project.order_service`.
- Keep controllers thin; place business logic in service classes.
- Use DTOs for request/response payloads at API boundaries.
- Prefer clear separation between controller, service, repository, and model layers.
- Keep configuration in `src/main/resources/application.yml` or environment-specific overrides.
- Use SLF4J logging with `LoggerFactory.getLogger(...)`.
- Handle errors with meaningful exception classes and centralized exception handling where appropriate.
- Keep code formatting consistent and avoid large blocks of commented-out code.

## 7. Development tips

- Use the Maven wrapper instead of installing Maven globally when possible.
- When changing service ports or URLs, update the relevant `application.yml` files.
- Start infrastructure services before the Spring Boot applications.
- If a service cannot register with Eureka, verify that the discovery server is already running.
- For Windows, use `./mvnw` from PowerShell or Git Bash; if the script is not executable, run `mvnw.cmd spring-boot:run`.
