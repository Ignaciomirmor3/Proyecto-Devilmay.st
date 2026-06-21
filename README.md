# devilmay.st 👕🛍️

**devilmay.st** es una plataforma de e-commerce moderna, escalable y dinámica diseñada para la gestión integral de ventas de indumentaria. Su arquitectura está basada en un ecosistema robusto de **6 microservicios independientes**, orquestados eficientemente y enrutados de forma centralizada por un API Gateway, garantizando alta disponibilidad y bajo acoplamiento entre sus componentes de negocio.

## 👥 Integrantes y Responsabilidades Principales
- **Joaquín Jerez:** Módulos de Catálogo, Carrito, Patrones DTO, Manejo de Excepciones Globales y automatización de despliegue.
- **Ignacio Miranda:** Módulo de Inventario, control de persistencia de stock y estados de bodega.
- **Kevin Vizcaya:** API Gateway, Módulo de Reservas y Módulo de Logística (arquitectura original de orquestación de pagos y entregas).

## 🚀 Funcionalidades Principales y Microservicios

- **API Gateway (Puerto 8080)**: Actúa como el punto de entrada único de la plataforma. Recibe todas las peticiones de los clientes y se encarga del enrutamiento dinámico hacia los diferentes microservicios internos.
- **Módulo de Catálogo (8081)**: Gestiona la vitrina digital de prendas. Permite listar, filtrar y visualizar los productos disponibles, sus detalles, géneros y tipos de prendas.
- **Módulo de Inventario (8082)**: Controla el stock físico de las prendas. Es responsable de la actualización en tiempo real de las cantidades disponibles cada vez que se reserva o se compra un producto.
- **Módulo de Carrito (8083)**: Maneja el almacenamiento temporal de las sesiones de compra de los usuarios, permitiendo agregar y validar exclusividad de prendas (mediante reglas DTO) antes del checkout.
- **Módulo de Reservas (8084)**: Coordina la generación de órdenes y citas de compra. Transforma el contenido del carrito en una reserva en firme, descontando el stock a través de comunicación síncrona.
- **Módulo de Logística (8085)**: Administra la preparación y el despacho de las reservas confirmadas, trazando el viaje del producto desde la bodega hasta el cliente final.

## 🛠️ Stack Tecnológico
- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.x / Spring Cloud Gateway
- **Base de Datos**: MySQL (XAMPP)
- **Dependencias Clave**: Spring WebFlux (WebClient), Lombok, Spring Data JPA, Spring Boot Validation.
- **Aplicaciones Requeridas**: Postman (para pruebas), XAMPP (servidor MySQL), IDE, Maven.

## 💻 Pasos para Ejecutar

1. **Preparar Base de Datos (XAMPP)**: 
   Abre el panel de control de XAMPP e inicia los servicios de Apache y MySQL. No necesitas crear las bases de datos manualmente, la configuración JDBC creará automáticamente `db_catalogo`, `db_inventario`, `db_carrito`, `db_reservas` y `db_logistica`.

2. **Clonar y Abrir el Repositorio**:
   ```bash
   git clone <url-del-repositorio>
   cd JoaquinJerez_KevinVizcaya_IgnacioMiranda_010D_Projectdevilmay.st