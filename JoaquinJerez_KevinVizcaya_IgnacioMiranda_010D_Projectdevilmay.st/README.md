# devilmay.st 👕🛍️

**devilmay.st** es una plataforma de e-commerce moderna, escalable y dinámica diseñada para la gestión integral de ventas de indumentaria. Su arquitectura está basada en un ecosistema robusto de **8 microservicios independientes**, orquestados eficientemente y enrutados de forma centralizada por un API Gateway, garantizando alta disponibilidad y bajo acoplamiento entre sus componentes de negocio.

## 👥 Integrantes y Responsabilidades Principales
- **Joaquín Jerez:** Módulos de Catálogo, Carrito, Patrones DTO, Manejo de Excepciones Globales y automatización de despliegue.
- **Ignacio Miranda:** Módulo de Inventario, control de persistencia de stock y estados de bodega.
- **Kevin Vizcaya:** API Gateway, Módulo de Reservas y Módulo de Logística (arquitectura original de orquestación de pagos y entregas).

## 🚀 Funcionalidades Principales y Microservicios

- **API Gateway (Puerto 8080)**: Actúa como el punto de entrada único de la plataforma. Recibe todas las peticiones de los clientes y se encarga del enrutamiento dinámico hacia los diferentes microservicios internos.
- **Módulo de Catálogo (8081)**: Gestiona la vitrina digital de prendas. Permite listar, filtrar y visualizar los productos disponibles.
- **Módulo de Inventario (8082)**: Controla el stock físico de las prendas.
- **Módulo de Carrito (8083)**: Maneja el almacenamiento temporal de las sesiones de compra de los usuarios, permitiendo agregar y validar exclusividad de prendas.
- **Módulo de Reservas (8084)**: Coordina la generación de órdenes y citas de compra.
- **Módulo de Logística (8085)**: Administra la preparación y el despacho de las reservas confirmadas.
- **Módulo de Cupones (8086)**: Permite la creación y validación de cupones de descuento para las compras.
- **Módulo de Reembolsos (8087)**: Gestiona las solicitudes de reembolso para órdenes previas, verificando su existencia y guardando motivos e información de pago.

## 🛠️ Stack Tecnológico
- **Lenguaje**: Java 21
- **Framework**: Spring Boot 3.x / Spring Cloud Gateway
- **Base de Datos**: MySQL (XAMPP)
- **Dependencias Clave**: Spring WebFlux (WebClient), Lombok, Spring Data JPA, Spring Boot Validation.
- **Aplicaciones Requeridas**: Postman (para pruebas), XAMPP (servidor MySQL), IDE, Maven.

## 💻 Pasos para Ejecutar

1. **Preparar Base de Datos (XAMPP)**: 
   Abre el panel de control de XAMPP e inicia los servicios de Apache y MySQL. No necesitas crear las bases de datos manualmente para los servicios originales, pero para Cupones y Reembolsos ejecuta los scripts:
   - `db_cupones.sql`
   - `db_reembolsos.sql`

2. **Ejecutar API Gateway y Microservicios**:
   Abre cada carpeta en el IDE e inicia sus aplicaciones principales.