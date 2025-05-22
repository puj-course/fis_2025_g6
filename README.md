# 🐾 Adóptame

## Descripción
Este proyecto es un sistema diseñado para facilitar y promover la adopción responsable de mascotas. Ayuda a conectar con los refugios y posibles adoptantes, promoviendo la transparencia y el bienestar animal. Además, permite manejar donaciones para apoyar a estos refugios.

## Características
- Registro y autenticación de usuarios (adoptantes, refugios y administradores).
- Visualización de mascotas disponibles para adopción.
- Gestión de mascotas por parte de los refugios.
- Proceso digitalizado de adopción.
- Sistema de donaciones para apoyar a los refugios.

## Objetivos
- **Fomentar la adopción responsable** de mascotas.
- **Reducir la población de animales en la calle**.
- **Concientización** sobre la importancia del bienestar animal y la problemática actual.
- **Optimizar el proceso de adopción** mediante herramientas digitales.

## Roles
- **Adoptante:** Puede registrarse, ver mascotas, filtrar y solicitar adopciones.
- **Refugio:** Puede gestionar mascotas, programar citas y realizar seguimientos posadopción.
- **Administrador:** Tiene control sobre la plataforma, usuarios y gestión de donaciones.

## Arquitectura
La arquitectura utilizada en el proyecto sigue el patrón *modelo-vista-controlador* (MVC) en Spring Boot, organizando las responsabilidades de la siguiente manera:

![arquitectura](diagramas/diagrama-arquitectura.png)

- **Controlador**. Maneja las solicitudes HTTP entrantes desde la vista o cliente (aplicación web) y devuelve las respuestas. Se comunica con los servicios para procesar la lógica de negocio.
- **Modelo**. Representa la lógica de negocio.
    - **Repositorios**. Accede a la base de datos con Spring Data JPA.
    - **Servicios**. Contiene la lógica y orquesta la interacción entre entidades, repositorios y otras clases.
    - **Entidades**. Representa las tablas de la base de datos. Utiliza DTO para transferir datos sin exponerse directamente y fábricas para crear los objetos.
- **Vista**. Aplicación web frontend en React que consume los endpoints REST.

### Flujo

1. La vista envía una solicitud HTTP al controlador.
2. El controlador transforma la solicitud en DTO.
3. El servicio procesa el DTO y usa fábricas para crear entidades.
4. Las entidades se gestionan mediante repositorios.
5. El servicio devuelve el resultado al controlador.
6. El controlador responde a la vista.

## Patrones GoF
El código utiliza tres patrones GoF para resolver problemas comunes del diseño de software:
- ***Factory Method* (creacional)**. Usado para crear los diferentes tipos de usuarios (administrador, refugio y adoptante) que comparten atributos.
- ***Decorator* (estructural)**. Se implementa en el sistema de notificaciones para que cada notificador añada una funcionalidad adicional encadenada y pueda extender el comportamiento.
- ***State* (de comportamiento)**. Se utiliza para manejar los estados de las solicitudes (pendiente, aprobada, rechazada y cancelada) encapsulando la lógica de transición.

## Tecnologías utilizadas
- **Lenguajes de programación**:
    - *Java* (backend).
    - *JavaScript* (frontend).
- **Frameworks y librerías**:
    - *Spring Boot*: framework para desarrollo de aplicaciones web y microservicios.
    - *Lombok*: librería para procesar anotaciones y eliminar redundancia de código.
    - *JUnit*: framework para hacer pruebas unitarias de aplicaciones Java.
    - *Mockito*: framework para crear pruebas unitarias con objetos simulados.
    - *React*: librería para interfaces de usuario.
    - *Axios*: librería para realizar peticiones HTTP.
    - *Jest*: framework para hacer pruebas unitarias de JavaScript.
- **Plataformas y servicios**:
    - *PostgreSQL*: base de datos relacional orientada a objetos.
    - *Twilio*: plataforma para implementar aplicaciones de comunicación en sistemas web.
    - *JWT*: estándar basado en JSON para crear tokens de acceso y propagar privilegios.
    - *SonarQube*: plataforma para evaluar código fuente.
- **Otras herramientas**:
    - *Maven*: herramienta para administrar y construir proyectos de Java.
    - *JaCoCo*: herramienta para medir y reportar la cobertura de las pruebas en Java.
    - *NPM*: sistema de gestión de paquetes para Node.js.

## Uso del proyecto
Clona el repositorio:

```bash
git clone https://github.com/puj-course/fis_2025_g6.git
```

### Prerrequisitos
Para ejecución local:

- [Java](https://www.oracle.com/java/technologies/downloads/).
- [Maven](https://maven.apache.org/download.cgi).
- [Node.js](https://nodejs.org/es/download).
- [PostgreSQL](https://www.postgresql.org/download/).
- [Docker Desktop](https://www.docker.com/products/docker-desktop/).

### Instalación

#### Backend
Coloque su configuración en un archivo `application.properties` dentro de `src/main/resources`.

En el directorio raíz del proyecto, ejecute:
```bash
mvn "spring-boot:run"
```
El servidor iniciará en unos segundos en http://localhost:8080. Utilice `Ctrl-C` en la terminal para finalizarlo.

Puede visualizar la documentación de la API en el navegador en http://localhost:8080/swagger-ui/index.html#/.

#### Frontend
En otra terminal, ejecute:
```bash
cd front
npm install
npm start
```

Abra el navegador e ingrese a http://localhost:3000. Utilice `Ctrl-C` en la terminal para finalizarlo.

### Ejecución de pruebas
En el directorio raíz del proyecto, ejecute:
```bash
mvn test
```

Para ver el reporte de cobertura de JaCoCo, ejecute:

```bash
mvn clean verify jacoco:report
```

El reporte se generará en `target/site/jacoco/index.html`. Puede abrirlo en el navegador.

Para ver el reporte de cobertura personalizado, ejecute:
```bash
mvn exec:java "-Dexec.mainClass=com.fis_2025_g6.util.CoverageReportReader"
```

El reporte se generará en `target/site/jacoco/coverage-summary.txt`.

### Despliegue
Coloque su configuración en un archivo `application-docker.properties` dentro de `src/main/resources`.

En el directorio raíz del proyecto, ejecute:
```bash
mvn clean package
docker compose up --build
```

## Miembros del equipo de desarrollo
- [Jorge Esteban Gómez Zuluaga](https://github.com/jorgegz10).
- [Gerardo Elián Martínez Ramírez](https://github.com/Eliqn).
- [André Sebastian Landinez Forero](https://github.com/andreMD287).
- [Sara Rodríguez Urueña](https://github.com/Nataly1000).

## Contacto
Cualquier pregunta o sugerencia, contáctanos a través de nuestro [repositorio de GitHub](https://github.com/puj-course/fis_2025_g6).
