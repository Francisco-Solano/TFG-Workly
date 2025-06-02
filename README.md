# 📘 Workly – Gestor de Tareas Colaborativo

> Ciclo Formativo: Desarrollo de Aplicaciones Multiplataforma (DAM)  
> Autor: Francisco León Muñoz

---

## 📑 Índice

- [📌 Introducción](#introducción)
- [⚙️ Funcionalidades y tecnologías](#funcionalidades-y-tecnologías)
- [🛠️ Guía de instalación](#guía-de-instalación)
- [🚀 Guía de uso](#guía-de-uso)
- [📚 Documentación completa](#documentación-completa)
- [🎨 Enlace a la interfaz en Figma](#enlace-a-la-interfaz-en-figma)
- [🧾 Conclusión](#conclusión)
- [🤝 Contribuciones y agradecimientos](#contribuciones-y-agradecimientos)
- [📄 Licencia](#licencia)
- [📬 Contacto](#contacto)

---

## 📌 Introducción

**Workly** es una aplicación web de gestión de proyectos y tareas con enfoque colaborativo. Permite a usuarios registrar proyectos, añadir tareas y colaborar en tiempo real con otros usuarios.

### 🎯 Objetivos

- Facilitar la gestión de tareas mediante una interfaz visual estilo Kanban.
- Permitir colaboración entre usuarios en proyectos compartidos.
- Integrar autenticación segura con JWT y control de roles.

---

## ⚙️ Funcionalidades y Tecnologías

### 🧩 Funcionalidades principales

- Registro e inicio de sesión.
- Creación de proyectos, tablas (columnas) y tareas.
- Subtareas, fechas límite y prioridades.
- Invitación y gestión de colaboradores.
- Movimiento de tareas entre columnas.
- Control de estado (Pendiente / Completada).

### 🛠 Tecnologías utilizadas

- **Frontend:** React + Vite
- **Backend:** Spring Boot + JWT
- **Base de Datos:** MySQL (modelo relacional)
- **Despliegue:** GitHub Pages (Frontend), Azure (Backend + BBDD)
- **Documentación:** Astro + Starlight
- **Calidad de código:** Sonar

---

## 🛠️ Guía de instalación

### 🚧 Requisitos

- Node.js v18+
- MySQL Server
- Java 17+
- IDE recomendado: VS Code + IntelliJ

### 📦 Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/workly.git
   ```

2. Instala dependencias del frontend:
   ```bash
   cd frontend
   npm install
   ```

3. Lanza el frontend:
   ```bash
   npm run dev
   ```

4. Abre el backend en IntelliJ o Spring Tool Suite  
   → Configura tus credenciales de BBDD  
   → Ejecuta la clase principal para levantar la API REST.

---

## 🚀 Guía de uso

1. Regístrate como nuevo usuario.
2. Crea un proyecto desde tu panel.
3. Añade columnas (tablas) al proyecto.
4. Añade tareas y subtareas dentro de cada tabla.
5. Invita a colaboradores por correo.
6. Cambia el estado de las tareas, muévelas y edítalas cuando lo necesites.

---

## 📚 Documentación completa

Puedes consultar la documentación técnica, diagramas y casos de prueba en:

🔗 [Documentación Workly (Starlight)](https://tudocumentacion.starlight.dev)

---

## 🎨 Enlace a la interfaz en Figma

🔗 [Diseño UI – Workly en Figma](https://figma.com/file/tu-enlace)

---

## 🧾 Conclusión

Workly surge como solución a la necesidad de organizar tareas de manera clara, visual y colaborativa. Gracias a su arquitectura modular y su enfoque FullStack, permite escalar fácilmente el sistema e integrarlo con nuevas funcionalidades en el futuro.

---

## 🤝 Contribuciones y agradecimientos

- ✍️ Desarrollado por: Francisco León Muñoz  
- 👨‍🏫 Tutor del TFG: [Nombre del tutor]  
- 🛠️ Herramientas y frameworks open source utilizados.

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.  
Consulta el archivo `LICENSE` para más información.

---

## 📬 Contacto

📧 francisco.leonmu@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/franciscoleon)

