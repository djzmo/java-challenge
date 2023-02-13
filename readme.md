## How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used! You can access the url below for testing it :

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 UI: http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

## What I did

1. Unified `EmployeeController` endpoints with standard JSON responses and clearer request data structure
2. Separated DTOs (used in controllers) from JPA entities to avoid exposing internal data structures
3. Added `CommonExceptionHandler` for standardized error responses and logging
4. Added Caffeine caching for `EmployeeService`
5. Added basic authentication for all REST endpoints (set username and password in `application.properties`)
6. Fixed Swagger to show only endpoints from the application REST controllers (see `SwaggerConfiguration`)
7. Upgraded to the latest Spring Boot 2 version (2.7.8 as of writing this)
8. Added tests for `EmployeeService` and `EmployeeController`

## My experience with Java

- I have been using Java occasionally since college (2011~) for studying
- I have been using Java 8 and 11 professionally since 2019 and started using Kotlin as well since 2020 with both Maven and Gradle
- I started using Spring Boot since 2021 have always been using it since then with Java and Kotlin
- I have used Spring Boot for creating public REST APIs and private microservices
- I can navigate through most Spring Boot projects, but always consults the documentation and look for best practices before making any contributions
