# Movie Reservation REST API 🍿

This is a complete backend service for a modern movie reservation platform, built with Java and the Spring Boot framework. It provides a full suite of RESTful API endpoints for user authentication, movie and showtime management, and a robust seat reservation system designed to prevent overbooking. The movie catalog is populated by integrating with the external OMDb API.

## Features ✨

  * **Secure User Authentication:** Full sign-up and login functionality using **JWT (JSON Web Tokens)** for stateless, secure API access.
  * **Role-Based Authorization:** Clear distinction between `ROLE_USER` and `ROLE_ADMIN` using Spring Security, protecting administrative endpoints.
  * **OMDb API Integration:** Admins can search for movies on OMDb by their IMDb ID and import them directly into the local database, automatically populating titles, descriptions, genres, and posters.
  * **Movie & Showtime Management:** Admin-only endpoints to manage the local movie catalog and schedule showtimes for different movies in specific cinema halls.
  * **Complex Reservation Logic:** A transactional reservation system that allows users to book specific seats for a showtime. A database-level unique constraint on `(showtime_id, seat_number)` provides a final guarantee against overbooking.
  * **Dynamic Seat Availability:** Endpoints to view available seats for any given showtime, enabling a frontend to render a visual seat map.
  * **User & Admin Views:** Users can view their upcoming reservations, while admins have endpoints for reporting on revenue and theater capacity.

## Technology Stack 🛠️

  * **Backend:** Java 17+, Spring Boot 3
  * **Security:** Spring Security 6, JSON Web Tokens (JJWT)
  * **Data Persistence:** Spring Data JPA (Hibernate)
  * **Database:** PostgreSQL
  * **External API Client:** `RestTemplate` for OMDb integration
  * **Validation:** Jakarta Bean Validation (Hibernate Validator)
  * **Build Tool:** Apache Maven
  * **Utilities:** Lombok

## Prerequisites

Before you begin, ensure you have the following installed on your system:

  * **Java Development Kit (JDK)**: Version 17 or higher.
  * **Apache Maven**: For building the project and managing dependencies.
  * **PostgreSQL**: A running instance of the PostgreSQL database.
  * An **OMDb API Key**: Get a free one from [omdbapi.com/apikey.aspx](http://www.omdbapi.com/apikey.aspx).

## Getting Started 🚀

Follow these steps to get the application running on your local machine.

### 1\. Clone the Repository

```bash
git clone https://github.com/M-Sayed939/Movie-Reservation.git
cd Movie-Reservation
```

### 2\. Set Up the PostgreSQL Database

1.  Connect to PostgreSQL as a superuser:
    ```bash
    psql -U postgres
    ```
2.  Run the following SQL commands to create the database and a dedicated user:
    ```sql
    CREATE DATABASE movie_db;
    CREATE USER movie_user WITH PASSWORD 'your_secure_password';
    GRANT ALL PRIVILEGES ON DATABASE movie_db TO movie_user;
    GRANT ALL ON SCHEMA public TO movie_user; -- Required for table creation
    ```

### 3\. Configure the Application

Open the `src/main/resources/application.properties` file and update it with your database credentials, OMDb key, and a secure JWT secret.

```properties
# Server Configuration
server.port=8081

# PostgreSQL Database Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/movie_db
spring.datasource.username=movie_user
spring.datasource.password=your_secure_password

# JPA & Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Security Configuration
# Generate your own long, random key for this
app.jwt.secret=your-super-long-and-secure-jwt-secret-key
app.jwt.expiration-in-ms=864000000

# OMDb API Key
omdb.api.key=your_omdb_api_key_here
```

### 4\. Build and Run the Application

Use Maven to build and run the project. The `CommandLineRunner` will automatically create an initial admin user (`admin`/`adminpassword`).

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8081`.

## API Endpoints 📖

Authenticated endpoints require an `Authorization: Bearer <token>` header.

### Authentication (`/api/auth`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/signup` | Register a new user. | Public |
| `POST` | `/login` | Authenticate and get a JWT.| Public |

### Admin: Movie Management (`/api/admin`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/omdb/{imdbId}` | Preview a movie's details from OMDb. | Admin |
| `POST` | `/movies/import/{imdbId}` | Import a movie from OMDb to the database. | Admin |

### Public Movie & Showtime Browsing (`/api`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `GET` | `/movies` | Get all movies available in the system. | Public |
| `GET` | `/movies/{id}/showtimes?date=YYYY-MM-DD` | Get all showtimes for a movie on a specific date. | Public |
| `GET` | `/showtimes/{id}/seats` | Get the seat layout and availability for a showtime.| Public |

### Reservation Management (`/api/reservations`)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/` | Create a new reservation for a showtime. | User |
| `GET` | `/my-reservations`| Get the logged-in user's reservations. | User |
