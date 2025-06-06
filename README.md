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

**Workly** es una aplicación web de gestión de proyectos y tareas con enfoque colaborativo. Permite a usuarios registrar proyectos, añadir tareas y añadir colaboradores para organizar tu trabajo junto con otros usuarios.

### 🎯 Objetivos

- Facilitar la gestión de tareas mediante una interfaz visual estilo Kanban.
- Permitir colaboración entre usuarios en proyectos compartidos.
- Practicar lenguajes y tecnologías que conozco y descubir y aplicar otras nuevas.
- Implementar un proyecto serio y profesional que utilice todo lo necesario para crear una aplicación real. 

---

## ⚙️ Funcionalidades y Tecnologías

### 🧩 Funcionalidades principales

- Registro e inicio de sesión.
- Creación de proyectos, tablas (columnas) y tareas.
- Subtareas, fechas límite entre otras propiedades de la tarea a editar.
- Invitación y gestión de colaboradores.
- Movimiento de tareas entre columnas.
- Control de estado (Pendiente / Completada).

### 🛠 Tecnologías utilizadas

- **Frontend:** React + Vite + Tailwind CSS + TypeScript
- **Backend:** Spring Boot + JWT
- **Base de Datos:** MySQL Workbench 
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
   git clone -b main git@github.com:Francisco-Solano/TFG-Workly.git
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
   → Configura tus credenciales de BBDD en el application.properties  
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

🔗 [Documentación Workly (Starlight)](https://github.com/Francisco-Solano/Documentacion-TFG-Workly)

---
---

## 🛠️ Para instalar la documentacion

### 📦 Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Francisco-Solano/Documentacion-TFG-Workly.git
   ```

2. Ejecuta el sigiiente comando en la terminal del editor:
   ```bash
   npm install
   ```

3. Lanza el frontend y entra al enlace que devuelve:
   ```bash
   npm run dev
   ```
---

## 🚀 Guía de uso
En esta guia se enseñará al usuario a realizar las siguientes funciones:
1. Regístrarse como nuevo usuario o iniciar sesión.
2. Crear un proyecto desde el panel.
3. Añadir columnas (tablas) al proyecto.
4. Añadir tareas y subtareas dentro de cada tabla.
5. Invitar a colaboradores por correo.
6. Cambiar el estado de las tareas, moverlas y editarlas cuando lo necesite.


1. Registrarse: El usuario puede registrarse rellenando los campos que se indican en la imagen y pulsando el boton de registrarse. También puede iniciar sesión si ya tiene una cuenta pulsando el botón de iniciar sesión.
   ![Registro](images/registro.jpg)

2. Iniciar sesion: El usuario puede iniciar sesion si ya tiene una cuenta escribiendo el correo y la contraseña. Tambien puede registrarse si no tiene una cuenta.
    ![Inicio de sesión](images/inicioSesion.jpg)

3. Dentro del Home el usuario puede realizar las siguientes acciones:
    1. Usar la barra de busqueda para buscar los proyectos por nombre.
    2. Pulsar el boton para crear un proyecto, en el que solo escribiendo el nombre lo podrá crear.
    3. Haciendo clic en el proyecto entra a su vista donde podrá organizar su trabajo mediante tablas, tareas y subtareas.
   También puede cerrar sesón desde la bara lateral.

![Vista de Home](images/Home.jpg)
![Buscar proyectos](images/buscar.jpg)

4.Dentro de un proyecto, el usuario puede realizar las siguientes acciones: 
   1. Pulsando el botón de compartir, el usuario puede escribir el email del usuario para añadirlo como colaborador y puede eliminarlo desde la lista que se muestra pulsando el boton x. 
   2. Añadir una nueva tarjeta en la que poner tareas. Solo se necesita el nombre que se vaya a escribir para crearla exitosamente.
   3. Añadir tarea dentro de una tarjeta existente, solo hace falta escribir el nombre, para editar más la tarea se hace clic sobre ella.
   4. Editar tarea, haciendo clic sobre la tarea donde se abrirá una vista en la que el usuario puede hacer las siguientes modificaciones de las que hablaremos a          continuacion.
![Inicio de sesión](images/proyecto.jpg)
![image](https://github.com/user-attachments/assets/3e5ff571-9742-402a-933c-58f0406ca387)
![Añadir Colaboradores al proyecto](images/añadircolaboradores.jpg)
![Eliminar Colaboradores al proyecto](images/eliminarColaborador.jpg)
![Crear tarjeta nueva](images/crearTarjeta.jpg)

5.Dentro de una tarea, el usuario puede realizar las siguientes modificaciones (Pulsando el boton de guardar cambios seguardarán todos y para salir sin guardar cierra la vista).
   1. Cambiar el nombre, la fecha limite para completarla y el estado en el que se encuentra la tarea.
   2. Crear una subtarea escribiendo el nombre que tendrá. Se creará y se mostrará un checkbox para marcarla como completada. Tambien se puede eliminar haciendo clic en el         icono de la derecha.(Tip: Si el usuario marca una tarea como completada, el resto de subtareas se marcarán también como completadas automáticamente).


![Editar tarea](images/editar.jpg)
![Vista Subtarea](images/subtarea.jpg)
![Cambiar fecha limite](images/fecha.jpg)
![Cambiar estado de la tarea](images/estado.jpg)

---



## 🎨 Enlace a la interfaz en Figma

🔗 [Diseño UI – Workly en Figma](https://www.figma.com/design/EwBQ7LioisJiuxBq5dKKxE/Workly?node-id=0-1&t=JHXNdoMhQSzMUsh1-1)

---

## 🧾 Conclusión

Workly surge como solución a la necesidad de organizar tareas de manera clara, visual y colaborativa. Gracias a su arquitectura modular y su enfoque FullStack, permite escalar fácilmente el sistema e integrarlo con nuevas funcionalidades en el futuro.

---

## 🤝 Contribuciones y agradecimientos

- ✍️ Desarrollado por: Francisco León Muñoz  
- 👨‍👩‍👧‍👦 Agradecimineto al apoyo de mi familia  
- 🛠️ Herramientas y frameworks open source utilizados.

---

## 📄 Licencia

Este proyecto ha sido desarrollado con fines académicos como parte del TFG del ciclo DAM.  
No se permite su uso comercial sin consentimiento previo del autor.


---

## 📬 Contacto

📧 franciscosolanoleon@gmail.com
🔗 [LinkedIn](https://linkedin.com)

