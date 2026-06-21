# DevilMay.st V5 - Backend de Microservicios 👕🛍️

**devilmay.st** es una plataforma de e-commerce moderna, escalable y dinámica diseñada para la gestión integral de ventas de indumentaria exclusiva. Su arquitectura está basada en un ecosistema robusto de **11 microservicios independientes**, orquestados eficientemente y enrutados de forma centralizada por un API Gateway, garantizando alta disponibilidad y bajo acoplamiento entre sus componentes de negocio.

La regla central del dominio establece que cada producto representa una **pieza única**, gestionada a través del inventario para evitar la doble reserva o sobreventa. El flujo principal abarca desde la visualización del catálogo hasta la generación de la reserva. Una vez pagada externamente, el sistema administra la logística, cupones de descuento, comprobantes, calificaciones y devoluciones (con sus respectivos reembolsos monetarios).

---

## 👥 Integrantes y Responsabilidades Principales

El desarrollo de este robusto ecosistema fue dividido estratégicamente entre los miembros del equipo:

- **Joaquín Jerez:** Desarrollo e implementación de los módulos de **Catálogo**, **Carrito**, **Reembolsos** y **Promociones**.
- **Kevin Vizcaya:** Desarrollo e implementación del **API Gateway** centralizado, y los módulos de **Reservas**, **Logística**, **Devoluciones** y **Comprobantes**.
- **Ignacio Miranda:** Desarrollo e implementación del control de persistencia en el módulo de **Inventario**, además de los módulos de **Calificaciones** y **Autenticación**.

**Sección:** 010D (Desarrollo FullStack I)

---

## 🛠️ Stack Tecnológico y Bibliotecas
- **Lenguaje**: Java 21
- **Framework**: Spring Boot 3.x / Spring Cloud Gateway
- **Base de Datos**: MySQL (XAMPP / Server 8.0+)
- **Dependencias Clave**: 
  - *Spring WebFlux (WebClient)* para comunicación síncrona entre microservicios.
  - *Spring Data JPA / Hibernate* para persistencia.
  - *Jakarta Validation* para reglas DTO (`@NotNull`, `@Min`, etc.).
  - *Springdoc OpenAPI (Swagger)* para auto-documentación interactiva.
  - *JUnit 5 y Mockito* para Testing Unitario (Patrón AAA).

---

## 🚀 Listado de Microservicios y Rutas del Gateway

El `api-gateway` corre en el puerto **8080** y es el punto único de entrada. A continuación, las rutas para consumir la API REST:

1. **Catálogo** (8081) 👉 `http://localhost:8080/api/v1/productos`
2. **Inventario** (8082) 👉 `http://localhost:8080/api/v1/inventario`
3. **Carrito** (8083) 👉 `http://localhost:8080/api/v1/carritos`
4. **Reservas** (8084) 👉 `http://localhost:8080/api/v3/reservas`
5. **Logística** (8085) 👉 `http://localhost:8080/api/v4/logistica`
6. **Promociones** (8086) 👉 `http://localhost:8080/api/v1/cupones`
7. **Reembolsos** (8087) 👉 `http://localhost:8080/api/v1/reembolsos`
8. **Comprobantes** (8088) 👉 `http://localhost:8080/api/v1/comprobantes`
9. **Calificaciones** (8089) 👉 `http://localhost:8080/api/v1/calificaciones`
10. **Devoluciones** (8090) 👉 `http://localhost:8080/api/v1/devoluciones`

---

## 📖 Documentación Interactiva (Swagger)

La documentación está centralizada mediante OpenAPI 3 en el Gateway. Para visualizarla, asegúrate de levantar el **api-gateway** y el microservicio que desees consultar, luego ingresa en tu navegador a:

👉 **http://localhost:8080/webjars/swagger-ui/index.html**

Desde el menú desplegable en la esquina superior derecha, selecciona el microservicio específico (Ej: "Servicio Catalogo", "Servicio Promociones").

---

## 💻 Instrucciones de Instalación y Ejecución

### Ejecución Local:
1. **Base de Datos:** Inicia los servicios de Apache y MySQL (ej. usando XAMPP). El proyecto creará las tablas automáticamente vía Hibernate (`spring.jpa.hibernate.ddl-auto=update`). Puedes usar el archivo `poblamiento.sql` incluido para insertar datos de prueba.
2. Abre una terminal y navega hasta la carpeta del microservicio (ej. `cd service-catalogo/service_catalogo`).
3. Compila y ejecuta utilizando el Maven Wrapper:
   ```bash
   .\mvnw clean spring-boot:run
   ```
4. Repite el paso anterior para levantar el `api-gateway` y asegurar el enrutamiento central. *

