# 💅 La Esmaltería - Sistema de Gestión

¡Bienvenido/a al repositorio de **La Esmaltería**! Este es un sistema de escritorio desarrollado en **Java** diseñado específicamente para digitalizar y automatizar la gestión de un salón de belleza y uñas. Permite administrar el catálogo de servicios ofrecidos y llevar un control total de la agenda de turnos.

---

## 🚀 Características Principales

* **Gestión del Catálogo (Servicios):**
  * Crear, editar y eliminar servicios de la carta (ej: Kapping, Semipermanente, Esculpidas).
  * Asignación de categorías, precio y duración estimada para cada servicio.
* **Agenda de Turnos:**
  * Asignación de nuevos turnos a clientes vinculados directamente a un servicio del catálogo.
  * Control de estados del turno: `Pendiente`, `Pagado` y `Cancelado`.
  * Ventana modal de cobro integrada.
* **Reportes y Exportación:**
  * Exportación de la agenda diaria a un archivo de texto plano (`.txt`) limpio y estructurado para impresión o revisión rápida.
* **Interfaz Gráfica Premium:**
  * Diseño minimalista y moderno usando Java Swing, con colores corporativos (rosa pastel y gris oscuro) e interacciones intuitivas para mejorar la experiencia de usuario (UX).

---

## 🛠️ Tecnologías y Arquitectura

El sistema está construido bajo el paradigma de **Programación Orientada a Objetos (POO)** y utiliza una estricta separación de responsabilidades a través de una **Arquitectura de 3 Capas**:

1. **Capa de Interfaz Gráfica (IGU):** Construida con `Java Swing` para las ventanas, paneles y diálogos.
2. **Capa Lógica:** Contiene las clases de dominio (`Turno`, `Servicio`) y la `Controladora` que maneja las reglas del negocio.
3. **Capa de Persistencia:** Utiliza **JPA (Java Persistence API)** para mapear los objetos de Java hacia tablas en la base de datos relacional.

## ⚙️ Requisitos para ejecutar

1. **Java Development Kit (JDK)** versión 25 o superior instalado.
2. **Apache Netbeans** (o cualquier otro IDE compatible con proyectos Maven).
3. Servidor de Base de Datos local (ej: **XAMPP** con MySQL/MariaDB en el puerto 3306).
4. Crear una base de datos vacía llamada `esmalteria_db` (o según lo configurado en el archivo `persistence.xml`).

---

## 👩‍💻 Cómo empezar

1. Clona este repositorio o descarga el código fuente.
2. Abre tu panel de XAMPP y enciende los módulos de **Apache** y **MySQL**.
3. Abre el proyecto desde Apache Netbeans.
4. Presiona el botón de **"Clean and Build"** (el ícono del martillo y la escoba) para que Maven descargue las librerías de JPA e Hibernate automáticamente.
5. Ejecuta el archivo principal `Laesmalteria.java` ubicado en el paquete base. ¡JPA creará las tablas en tu base de datos automáticamente al arrancar!

---
*Desarrollado como proyecto de Programación Orientada a Objetos.*
