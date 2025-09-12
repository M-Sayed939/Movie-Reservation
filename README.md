# Movie Reservation REST API 🍿

This is a complete backend service for a modern movie reservation platform, built with Java and the Spring Boot framework. It provides a full suite of RESTful API endpoints for user authentication, movie and showtime management, and a robust seat reservation system designed to prevent overbooking. The movie catalog is populated by integrating with the external TMDb API.

This backend is the engine for the **[ Angular Frontend](https://github.com/M-Sayed939/Movie-Reservation-FrontEnd)**.

### Preview

**Movie Browsing Page**

**Reservation Page**

## Features ✨

* **Secure User Authentication:** Full sign-up and login functionality using **JWT (JSON Web Tokens)** for stateless, secure API access.
* **Role-Based Authorization:** Clear distinction between `ROLE_USER` and `ROLE_ADMIN` using Spring Security, protecting administrative endpoints.
* **TMDb API Integration:** Admins can import a list of popular, "now playing" movies directly from The Movie Database (TMDb), automatically populating titles, descriptions, genres, and posters.
* **Movie & Showtime Management:** Admin-only endpoints to manage the local movie catalog and schedule showtimes for different movies in specific cinema halls.
* **Complex Reservation Logic:** A transactional reservation system that allows users to book specific seats for a showtime. A database-level unique constraint on `(showtime_id, seat_number)` provides a final guarantee against overbooking.
* **Dynamic Seat Availability:** Endpoints to view available seats for any given showtime, enabling a frontend to render a visual seat map.

## Technology Stack 🛠️

* **Backend:** Java 17+, Spring Boot 3
* **Security:** Spring Security 6, JSON Web Tokens (JJWT)
* **Data Persistence:** Spring Data JPA (Hibernate)
* **Database:** PostgreSQL
* **External API Client:** `RestTemplate` for TMDb integration
* **Validation:** Jakarta Bean Validation (Hibernate Validator)
* **Build Tool:** Apache Maven
* **Utilities:** Lombok

## Getting Started 🚀

### Prerequisites

* **Java Development Kit (JDK)**: Version 17 or higher.
* **Apache Maven**: For building the project.
* **PostgreSQL**: A running instance of the database.
* A **TMDb API Key**: Get a free one from [themoviedb.org](https://www.themoviedb.org/signup).

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
2.  Run the following SQL commands:
    ```sql
    CREATE DATABASE movie_db;
    CREATE USER movie_user WITH PASSWORD 'your_secure_password';
    GRANT ALL PRIVILEGES ON DATABASE movie_db TO movie_user;
    GRANT ALL ON SCHEMA public TO movie_user; -- Required for table creation
    ```

### 3\. Configure the Application

Open `src/main/resources/application.properties` and update it with your database credentials, TMDb key, and a secure JWT secret.

```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/movie_db
spring.datasource.username=movie_user
spring.datasource.password=your_secure_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
app.jwt.secret=your-super-long-and-secure-jwt-secret-key
app.jwt.expiration-in-ms=864000000
tmdb.api.key=your_tmdb_api_key_here
```

### 4\. Build and Run

The application includes a `CommandLineRunner` that will automatically create an initial admin user (`admin`/`adminpassword`) on the first startup.

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8081`.

## API Endpoints 📖

Authenticated endpoints require an `Authorization: Bearer <token>` header.

### Authentication (`/api/auth`)

| Method | Endpoint  | Description                       | Access |
| :----- | :-------- | :-------------------------------- | :----- |
| `POST` | `/signup` | Register a new user.              | Public |
| `POST` | `/login`  | Authenticate and get a JWT.       | Public |

### Admin: Movie & Showtime Management (`/api/admin`)

| Method | Endpoint                      | Description                               | Access |
| :----- | :---------------------------- | :---------------------------------------- | :----- |
| `POST` | `/movies/import-popular`      | Import a list of popular movies from TMDb.| Admin  |
| `POST` | `/theaters`                   | Create a new cinema hall (theater).       | Admin  |
| `POST` | `/showtimes`                  | Schedule a new showtime for a movie.      | Admin  |

### Public Movie & Showtime Browsing (`/api`)

| Method | Endpoint                        | Description                                     | Access |
| :----- | :------------------------------ | :---------------------------------------------- | :----- |
| `GET`  | `/movies`                       | Get all movies available in the system.         | Public |
| `GET`  | `/movies/{id}`                  | Get the details of a single movie.              | Public |
| `GET`  | `/movies/{id}/showtimes?date=...` | Get showtimes for a movie on a specific date.   | Public |
| `GET`  | `/showtimes/{id}/seats`         | Get seat availability for a specific showtime.  | Public |

### Reservation Management (`/api/reservations`)

| Method | Endpoint           | Description                             | Access |
| :----- | :----------------- | :-------------------------------------- | :----- |
| `POST` | `/`                | Create a new reservation for a showtime.| User   |
| `GET`  | `/my-reservations` | Get the logged-in user's reservations.  | User   |