# API de Mascota Virtual - Backend

## Descripció

Aquesta és l'API per gestionar mascotes virtuals, incloent crear, eliminar i actualitzar mascotes. Els usuaris poden registrar-se, iniciar sessió, i gestionar les seves pròpies mascotes. Els administradors poden gestionar totes les mascotes i usuaris del sistema.

Aquest backend està dissenyat per conectar-se amb un frontend desenvolupat en TypeScript disponible al seguent repositori: [Repositori de Frontend per Mascotes Virtuals](https://github.com/SergiSancho/mascota-virtual-frontend).

## Enllaç Swagger

Podeu accedir a la documentació de l'API a través del següent enllaç: [Swagger UI](http://localhost:8080/swagger-ui.html)

## Servidors

- **URL del servidor local**: `http://localhost:8080`

---

## Endpoints

### Controlador d'Administració
Aquest controlador permet als administradors gestionar mascotes i usuaris.

#### **Obtenir tots els usuaris**

- **Mètode HTTP**: `GET`
- **Ruta**: `/api/admin/users`
- **Descripció**: Retorna la llista de tots els usuaris del sistema.

#### **Obtenir totes les mascotes**

- **Mètode HTTP**: `GET`
- **Ruta**: `/api/admin/mascotes`
- **Descripció**: Retorna la llista de totes les mascotes del sistema.

#### **Obtenir detalls d'una mascota**

- **Mètode HTTP**: `GET`
- **Ruta**: `/api/admin/mascotes/{mascotaId}`
- **Descripció**: Retorna els detalls d'una mascota específica a partir del seu ID.

#### **Eliminar una mascota**

- **Mètode HTTP**: `DELETE`
- **Ruta**: `/api/admin/mascotes/{mascotaId}`
- **Descripció**: Elimina una mascota específica a partir del seu ID.

#### **Eliminar un usuari**

- **Mètode HTTP**: `DELETE`
- **Ruta**: `/api/admin/users/{userId}`
- **Descripció**: Elimina un usuari específic a partir del seu ID.

---

### Controlador de Mascota (Usuari)
Aquest controlador permet als usuaris gestionar les seves pròpies mascotes virtuals.

#### **Obtenir detalls d'una mascota (usuari autenticat)**

- **Mètode HTTP**: `GET`
- **Ruta**: `/api/users/mascotes/{mascotaId}`
- **Descripció**: Retorna els detalls d'una mascota de l'usuari autenticat.

#### **Actualitzar una mascota**

- **Mètode HTTP**: `PUT`
- **Ruta**: `/api/users/mascotes/{mascotaId}`
- **Descripció**: Actualitza els detalls d'una mascota de l'usuari autenticat.

#### **Eliminar una mascota (usuari autenticat)**

- **Mètode HTTP**: `DELETE`
- **Ruta**: `/api/users/mascotes/{mascotaId}`
- **Descripció**: Elimina una mascota de l'usuari autenticat.

#### **Obtenir totes les mascotes de l'usuari**

- **Mètode HTTP**: `GET`
- **Ruta**: `/api/users/mascotes`
- **Descripció**: Retorna totes les mascotes creades per l'usuari autenticat.

#### **Crear una nova mascota**

- **Mètode HTTP**: `POST`
- **Ruta**: `/api/users/mascotes`
- **Descripció**: Crea una nova mascota per a l'usuari autenticat.

---

### Controlador d'Autenticació
Aquest controlador permet als usuaris registrar-se i iniciar sessió.

#### **Registrar un nou usuari**

- **Mètode HTTP**: `POST`
- **Ruta**: `/api/auth/register`
- **Descripció**: Registra (crea) un nou usuari al sistema.

#### **Iniciar sessió**

- **Mètode HTTP**: `POST`
- **Ruta**: `/api/auth/login`
- **Descripció**: Inicia sessió amb el nom d'usuari i contrasenya, retornant un token JWT.

---

## Schemas

### MascotaDTO
Aquest esquema defineix la mascota virtual, incloent propietats com nom, color, tipus, energia, i estat.

### AuthRequest
Aquest esquema defineix la sol·licitud d'autenticació, incloent el nom d'usuari i la contrasenya.

### AuthResponse
Aquest esquema defineix la resposta d'autenticació, incloent el token JWT.

### UsuariDTO
Aquest esquema defineix l'usuari del sistema, incloent el nom d'usuari i el rol (USER o ADMIN).

---

## Instal·lació

   ```bash
   git clone https://github.com/SergiSancho/mascota-virtual-backend.git
   cd mascota_virtual_back
   mvn spring-boot:run 
   ```
## Requisits

- Java 17 o superior.
- Maven 3.6.3 o superior.
- MongoDB: Assegura't de tenir una instància de MongoDB corrent localment o canvia la configuració a application.properties:

spring.data.mongodb.uri=mongodb://localhost:27017/mascota_virtual_db

## Llicència

Aquest projecte està sota la llicència GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.html).

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web.reactive)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.nosql.mongodb)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web.security)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#using.devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

