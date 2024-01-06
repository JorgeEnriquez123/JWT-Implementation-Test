# Spring Security + JWT Test implementation
This project demostrates a basic implementation of JWT Security with Spring Security. It effectively handles both Authentication and Authorization exceptions within the JWT verification process, and includes comprehensive Logging.

In this project, the JWT implementation is **not** entirely **STATELESS** since we are making a database call on each Authentication process to retrieve the User's roles, ensuring that the User's permissions are up-to-date whenever they attempt to reach a secured endpoint.
This approach eliminates the need of revoking a JWT and gives us greater control over our users.

Caching Data can help to improve performance in this approach, but it's not being used in this project.

### Features:
* Initial Users and Roles Loaded. (USER + ADMIN)
* Simple RestController returning a message.
* Role-Based Access Control.
* Logging.
* JWT Exception Handling.
* Authentication Error Handling.
* Authorization Error Handling.

### Technologies
* **[Hibernate](https://hibernate.org)** as ORM for database interactions.
* **[JPA](https://en.wikipedia.org/wiki/Jakarta_Persistence)** for accessing and persisting data based on Java Objects.
* **[MySQL](https://www.mysql.com/)** database to record User Data.
* **[Lombok](https://projectlombok.org/features)** to reduce boilerplate code.
* **[JsonObjectBuilder](https://mvnrepository.com/artifact/org.glassfish/jakarta.json)** for building JSON Objects.

## Usage
### API Endpoints
* **GET** `/public`: returns a simple message as String.
* **GET** `/secured`: returns a simple message as String. **Requires Authentication.**
* **GET** `/secured/admin`: returns a simple message as String. **Requires ```ADMIN``` role.**
* **POST** `/auth/login`: Authenticates a user and returns a JWT token.

### Login Process
1. User makes a POST request to `/auth/login` with a [LoginRequestDto](https://github.com/JorgeEnriquez123/JWT-Implementation-Test/blob/main/src/main/java/com/jorge/jwtnewtest/dto/LoginRequestDto.java) Object as body.
```json
{
    "username": "Jorge",
    "password": "jorge123"
}
```
2. Server returns a [LoginResponseDto](https://github.com/JorgeEnriquez123/JWT-Implementation-Test/blob/main/src/main/java/com/jorge/jwtnewtest/dto/LoginResponseDto.java) Object that contains the Access Token.
```json
{
"access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb3JnZSIsImlhdCI6MTcwNDUyNzU4OCwiZXhwIjoxNzA0NTI3NjQ4fQ.ylqZkFGIyymfyyB9wzOEexsAM-w8TKDowvX5keNIyB4",
}
```
User needs to add the Access Token in all secured endpoints to gain authorization.