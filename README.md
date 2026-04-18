# Business Rules Matrix - Sistema de Matriz de Decisión

Sistema de reglas de negocio basado en Matriz de Decisión implementado con Spring Boot 4 y Arquitectura Hexagonal.

## Características

- ✅ Motor de reglas sin if-else anidados (Strategy + Factory Pattern)
- ✅ Configuración dinámica mediante YAML
- ✅ Arquitectura Hexagonal (Ports & Adapters)
- ✅ Principios SOLID y Clean Code
- ✅ API REST documentada con OpenAPI/Swagger
- ✅ Base de datos H2 en memoria con datos de prueba
- ✅ Tests unitarios

## Tecnologías

- Java 17
- Spring Boot 4.x
- Maven
- H2 Database
- Lombok
- SpringDoc OpenAPI

## Ejecutar el Proyecto

```bash
mvn clean install
mvn spring-boot:run
```

## Endpoints

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
- API Base: http://localhost:8080/api/v1
