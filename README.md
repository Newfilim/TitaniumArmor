📦 TitaniumArmor – Microservices System
🧭 Descripción del proyecto

TitaniumArmor es un sistema basado en arquitectura de microservicios desarrollado con Java y Spring Boot, diseñado para simular un flujo completo de negocio de ventas.

El sistema está compuesto por servicios independientes que se comunican a través de APIs REST y cada uno posee su propia base de datos, garantizando desacoplamiento, escalabilidad y mantenibilidad.

🧱 Arquitectura general

El sistema está dividido en los siguientes microservicios:

👤 Clientes Service
Gestión de clientes (registro, consulta, actualización).
📦 Catálogo Service
Manejo de productos y tipos de productos.
🏬 Inventario Service
Control de stock y disponibilidad de productos.
🛒 Ventas Service
Gestión de ventas y generación de transacciones.
💳 Pagos Service
Procesamiento de pagos asociados a ventas.
🗄️ Bases de datos

Cada microservicio cuenta con su propia base de datos independiente:

clientes_db
catalogo_db
inventario_db
ventas_db
pagos_db

👉 Esto sigue el principio de Database per Service, evitando dependencias directas entre servicios.

🔁 Flujo del sistema
Se registra un cliente
Se consultan productos del catálogo
Se valida disponibilidad en inventario
Se crea una venta
Se procesa el pago
Se actualiza el stock en inventario
🛠️ Tecnologías utilizadas
Java
Spring Boot
Spring Web (REST APIs)
Spring Data JPA
Bases de datos relacionales (una por servicio)
Maven
Arquitectura de microservicios
📡 Endpoints (resumen)

Cada microservicio expone endpoints REST como:

Clientes
GET /clientes
POST /clientes
GET /clientes/{id}
Catálogo
GET /productos
POST /productos
GET /tipos-producto
Inventario
GET /stock
PUT /stock/{productoId}
Ventas
POST /ventas
GET /ventas/{id}
Pagos
POST /pagos
GET /pagos/{id}
📌 Características del sistema
Arquitectura de microservicios
Independencia de bases de datos
Separación de responsabilidades
APIs REST
Escalabilidad por servicio
🚀 Cómo ejecutar el proyecto

Cada microservicio se ejecuta de forma independiente:

mvn spring-boot:run

O desde el IDE (IntelliJ / VSCode):

Ejecutar cada servicio como aplicación Spring Boot
📁 Estructura del repositorio
TitaniumArmor/
│
├── clientes/
├── catalogo/
├── inventario/
├── ventas/
├── pagos/
└── README.md
📈 Posibles mejoras futuras
Implementar API Gateway
Autenticación con JWT
Comunicación asíncrona (Kafka/RabbitMQ)
Dockerización de microservicios
Orquestación con Kubernetes
Centralización de logs y monitoreo
👨‍💻 Autor

Proyecto desarrollado como práctica de arquitectura de microservicios en Java Spring Boot.