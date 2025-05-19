Diagrama de despliegue

![Diagrama de Despliegue](../diagramas/diagrama-despliegue.png)

1. Cliente (Java App)
Representado como un componente llamado Cliente (Java app).
Dentro de este componente hay un archivo ejecutable app.jar, indicando que es una aplicación de escritorio o standalone escrita en Java.
Este cliente se comunica con el servidor (back-end) usando el protocolo HTTPS, lo que implica que la comunicación está cifrada y segura.

2. Back-end (Spring Boot, Docker)
Este componente es el núcleo del sistema, desarrollado con el framework Spring Boot y desplegado mediante Docker.
Es el encargado de recibir las solicitudes del cliente y orquestar el flujo de datos entre los distintos servicios del sistema.
Expone endpoints seguros a través de HTTPS y maneja la lógica de negocio.
Tiene conexiones con tres servicios o componentes clave:

3. Base de datos
Representada con una forma cilíndrica (convención para bases de datos).
El back-end interactúa con esta base de datos usando SQL, lo que implica que es probablemente una base de datos relacional (como PostgreSQL, MySQL, etc.).
Aquí se almacenan datos persistentes como usuarios, registros, configuraciones, etc.

4. Autenticación
Este componente es responsable del proceso de autenticación de usuarios.
Usa JWT (JSON Web Tokens) para manejar la autenticación:
El back-end emite un token JWT cuando un usuario se autentica correctamente.
Este token puede ser usado por el cliente en las siguientes solicitudes como prueba de identidad.
La comunicación entre el back-end y el componente de autenticación es mediante el envío y validación de tokens JWT.

5. Notificaciones
Este componente se encarga de enviar notificaciones, posiblemente por SMS, email o push.
El back-end se comunica con este componente usando Twilio, una plataforma muy utilizada para mensajería y notificaciones.
Esto sugiere que el sistema puede notificar al usuario en tiempo real o como parte de un flujo de trabajo (por ejemplo, confirmaciones, alertas, recordatorios, etc.).

6. Flujo general del sistema
El usuario ejecuta la aplicación Java (app.jar).
Se establece una conexión segura (HTTPS) con el back-end.
Si se requiere autenticación, el back-end interactúa con el componente de Autenticación para validar credenciales y emitir un token JWT.
El back-end realiza consultas o actualizaciones a la base de datos usando SQL.
Si es necesario notificar al usuario, el back-end utiliza Twilio para enviar mensajes a través del componente de Notificaciones.

7. Tecnologías clave involucradas
Java (Cliente)
Spring Boot (Back-end)
Docker (Contenedorización)
SQL (Base de datos relacional)
JWT (Seguridad/autenticación)
Twilio (Notificaciones)
