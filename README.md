# Spring Security + JWT Test implementation
This project is a test of implementing a basic JWT Security with Spring Security. It handles both Authenticaion and Authorization exceptions within JWT verification process, adding up its respective Logging.

The JWT implementation in this project is **not** entirely **STATELESS** since we are making a database call to retrieve the User's roles at the current time, ensuring that User's permissions are up-to-date whenever they try to access the system.
This approach removes the need of revoking a JWT and let us have a better control over the Users.

### Features:
* Initial Users and Roles Loaded. (USER + ADMIN)
* Simple RestController returning a message.
* Role-Based Access Control.
* Logging
* JWT Exception Handling.
* Authentication Error Handling.
* Authorization Error Handling.

### Technologies
* **[Hibernate](https://hibernate.org)** as ORM for database interactions.
* **[JPA](https://en.wikipedia.org/wiki/Jakarta_Persistence)** for accessing and persisting data based on Java Objects
* **[MySQL](https://www.mysql.com/)** database to record User Data.
* **[Lombok](https://projectlombok.org/features)** to reduce boilerplate code
* **[JsonObjectBuilder](https://mvnrepository.com/artifact/org.glassfish/jakarta.json)** for building JSONs