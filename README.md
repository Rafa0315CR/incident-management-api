# Incident Management API

API REST para registrar, asignar, consultar y dar seguimiento a incidencias operativas. El proyecto demuestra una arquitectura backend organizada, validación de datos, persistencia SQL, documentación OpenAPI y pruebas automatizadas.

## Funcionalidades

- Crear, consultar, actualizar y eliminar incidencias.
- Cambiar el estado de una incidencia durante su ciclo de vida.
- Filtrar por estado y prioridad.
- Paginar y ordenar resultados.
- Validar solicitudes y devolver errores consistentes.
- Explorar y probar la API desde Swagger UI.
- Ejecutar localmente con H2 o mediante Docker con PostgreSQL.

## Tecnologías

- Java 21
- Spring Boot 3
- Spring Web y Bean Validation
- Spring Data JPA
- H2 y PostgreSQL
- OpenAPI / Swagger UI
- JUnit 5, MockMvc y Mockito
- Docker y Docker Compose

## Arquitectura

```text
Controller -> Service -> Repository -> Database
                |
                +-> DTOs and business rules
```

El código se organiza por funcionalidad para mantener juntos el modelo, el repositorio, el servicio, el controlador y sus contratos de entrada y salida.

## Ejecución local

Requisitos: Java 21 y Maven 3.9 o superior.

```bash
mvn spring-boot:run
```

La aplicación utiliza una base H2 en memoria. Swagger UI estará disponible en:

```text
http://localhost:8080/swagger-ui.html
```

## Ejecución con Docker

```bash
docker compose up --build
```

Este modo inicia la API y PostgreSQL. La aplicación queda disponible en `http://localhost:8080`.

## Endpoints principales

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/v1/incidents` | Lista y filtra incidencias |
| `GET` | `/api/v1/incidents/{id}` | Obtiene una incidencia |
| `POST` | `/api/v1/incidents` | Crea una incidencia |
| `PUT` | `/api/v1/incidents/{id}` | Actualiza una incidencia |
| `PATCH` | `/api/v1/incidents/{id}/status` | Cambia el estado |
| `DELETE` | `/api/v1/incidents/{id}` | Elimina una incidencia |

### Ejemplo de creación

```json
{
  "title": "API de clientes no disponible",
  "description": "El servicio devuelve errores al consultar clientes.",
  "priority": "HIGH",
  "reporter": "Operaciones",
  "assignee": "Equipo backend"
}
```

Estados permitidos: `OPEN`, `IN_PROGRESS`, `RESOLVED` y `CLOSED`.

Prioridades permitidas: `LOW`, `MEDIUM`, `HIGH` y `CRITICAL`.

## Pruebas

```bash
mvn test
```

El proyecto incluye pruebas unitarias de la capa de servicio y pruebas de integración de los endpoints.

## Propósito

Este proyecto fue creado como demostración técnica de desarrollo backend con Java. No utiliza información, código ni procesos confidenciales de ninguna organización.
