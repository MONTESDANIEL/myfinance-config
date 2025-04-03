# ⚙️ MyFinance Config

## 📌 Descripción

Este repositorio contiene el **microservicio de configuración** de **MyFinance**, encargado de gestionar la personalización de la aplicación.  
Permite a los usuarios ajustar colores, etiquetas y preferencias dentro de la plataforma para adaptar la experiencia a sus necesidades.

Este microservicio está desarrollado con **Spring Boot**, proporcionando una API REST segura y eficiente para manejar las configuraciones de los usuarios.

---

## ✨ Características Principales

- ✅ **Gestión de Colores** – Personalización del esquema de colores en la interfaz.
- ✅ **Etiquetas Personalizadas** – Creación y modificación de etiquetas para categorizar movimientos financieros.
- ✅ **Preferencias de Usuario** – Almacenamiento de configuraciones individuales para cada usuario.
- ✅ **APIs Seguras** – Protección con autenticación basada en JWT.
- ✅ **Base de Datos SQL** – Persistencia eficiente de las configuraciones.

---

## 🛠 Tecnologías Utilizadas

- **Spring Boot** – Framework para el desarrollo del backend.
- **Spring Security & JWT** – Manejo de autenticación segura.
- **Spring Data JPA** – Interacción con la base de datos.
- **MySQL** – Base de datos relacional para almacenamiento.
- **Docker** – Contenedorización del microservicio.

---

## 🚀 Instalación y Ejecución

### 📌 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **JDK 17 o superior**
- **Maven**
- **Docker** (opcional)
- **Base de datos MySQL**

### 📥 Clonar el Repositorio

```sh
git clone https://github.com/MONTESDANIEL/myfinance-config.git
cd myfinance-config
```

### 🗃️ Configurar la base de datos

```sh
Utilizar el archivo .sql del proyecto para generar la base.
```

### ⚙️ Configurar el application.properties

Ajustar el application.properties de la siguiente forma según la base de datos:

```sh
spring.datasource.url=           # Url de acceso a la base de datos.
spring.datasource.username=      # Usuario de la base de datos
spring.datasource.password=      # Contraseña de la base de datos
```

### 📦 Construir y Ejecutar el Proyecto

Para compilar y ejecutar el proyecto:

```sh
mvn clean install
mvn spring-boot:run
```

---

## 📂 Estructura del Proyecto

```sh
myfinance-config/
│── src/main/java/com/myfinance/backend/config/
│   ├── config/      # Configuración de seguridad
│   ├── controllers/  # Controladores REST
│   ├── entities/    # Entidades
│   ├── service/     # Lógica de negocio
│   ├── exceptions/  # Control de excepciones
│   ├── repositories/  # Acceso a la base de datos
│   ├── services/    # Configuración de autenticación
│── src/main/java/com/myfinance/backend/config/resources/
│   ├── application.properties  # Configuración del microservicio
│── Dockerfile     # Configuración para contenedorización
│── config_db.sql  # Archivo de creación de la base de datos
│── README.md      # Documentación del repositorio
```

## 📜 Licencia

Este proyecto está bajo la licencia MIT, por lo que puedes usarlo y modificarlo libremente.

## ⛓️Relacionado

🔗 Repositorio Principal: [MyFinance](https://github.com/MONTESDANIEL/myfinance)
