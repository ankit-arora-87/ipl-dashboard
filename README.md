# IPL Dashboard

A full-stack web application for browsing and analyzing Indian Premier League (IPL) match data from 2008 to 2020.

## Architecture

The application consists of two parts:

- **Backend**: Spring Boot REST API (Java 11)
- **Frontend**: React single-page application

## Backend

### Tech Stack

- **Java 11** with **Spring Boot 2.4.5**
- **Spring Data JPA** for database access
- **Spring Batch** for CSV data processing
- **Spring Web** for REST APIs
- **Spring HATEOAS** for hypermedia-driven REST APIs
- **SpringDoc OpenAPI** (swagger-ui) for API documentation
- **H2 Database** (in-memory, with MySQL configuration available)
- **Maven** for build management

### Project Structure

```
src/main/java/com/learncodetogether/ipldashboard/
├── IplDashboardApplication.java          # Main entry point
├── batchprocessor/
│   └── MatchDataProcessor.java           # Spring Batch item processor for match data
├── config/
│   └── BatchProcessingConfig.java        # Batch job configuration (reader, processor, writer)
├── controller/
│   ├── DashboardController.java          # REST controller for paginated match listing
│   ├── TeamController.java               # REST controller for teams and team matches
│   └── UserController.java               # REST controller for user CRUD operations
├── data/
│   └── MatchInputData.java               # DTO for CSV input data
├── exceptions/
│   ├── AppResponseEntityHandler.java     # Global exception handler
│   ├── ExceptionResponse.java            # Error response model
│   └── UserNotFoundException.java        # Custom exception
├── listener/
│   └── JobCompletionNotificationListener.java  # Batch job completion listener
├── model/
│   ├── Match.java                        # Match entity
│   ├── Team.java                         # Team entity
│   └── User.java                         # User entity
├── repository/
│   ├── implementations/
│   │   ├── MatchCustomRepositoryImpl.java
│   │   └── UserRepo.java
│   ├── DashboardRepoInterface.java
│   ├── MatchCustomRepository.java
│   ├── MatchRepository.java
│   ├── TeamRepositoryInterface.java
│   └── UserRepoInterface.java
├── service/
│   └── implementations/
│       ├── DashboardService.java
│       ├── MatchService.java
│       ├── TeamService.java
│       └── UserService.java
│   ├── DashboardServiceInterface.java
│   ├── MatchServiceInterface.java
│   ├── TeamServiceInterface.java
│   └── UserServiceInterface.java
└── specification/
    └── MatchSpecification.java           # JPA Specification for dynamic queries
```

### Database

The application uses an H2 in-memory database by default. MySQL configuration is also available (commented out in `application.properties`).

**Match data** from IPL seasons 2008–2020 is loaded at startup from `src/main/resources/ipl-matches-2008-2020.csv` using a Spring Batch job.

### API Endpoints

| Method | Endpoint                         | Description                     |
|--------|----------------------------------|---------------------------------|
| GET    | `/api/v1/teams`                  | List all teams                  |
| GET    | `/api/v1/teams/{name}/{size}`    | Get team details with recent matches |
| GET    | `/api/v1/teams/{name}/matches`   | Get matches for a team (filterable) |
| GET    | `/api/v1/dashboard`              | Paginated match listing         |
| GET    | `/api/v1/users`                  | List all users                  |
| GET    | `/api/v1/users/{id}`             | Get user details (HATEOAS)      |
| POST   | `/api/v1/users`                  | Create a new user               |
| DELETE | `/api/v1/users/{id}`             | Delete a user                   |

#### Match Query Parameters

`GET /api/v1/teams/{name}/matches` supports:

| Parameter | Type    | Default | Description               |
|-----------|---------|---------|---------------------------|
| `year`    | int     | -1      | Filter by year (all if -1) |
| `against` | string  | "all"   | Filter by opponent team   |
| `result`  | string  | "all"   | Filter by match result    |

### Configuration

Key settings in `src/main/resources/application.properties`:

- **Port**: 8081
- **Database**: H2 in-memory (console available at `/h2-console`)
- **JPA DDL**: `create` (schema auto-created at startup)

### Running the Backend

```bash
# Using Maven
./mvnw spring-boot:run

# Or build and run the JAR
./mvnw clean package -DskipTests
java -jar target/ipl-dashboard-0.0.1-SNAPSHOT.jar
```

### API Documentation

Once running, OpenAPI documentation is available at:

- **Swagger UI**: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## Frontend

### Tech Stack

- **React** (Create React App)
- **React Router** for client-side routing

### Project Structure

```
src/frontend/
├── public/
└── src/
    ├── App.js                            # Main app with routing
    ├── App.css
    ├── index.js
    ├── components/
    │   ├── MatchDetailCard.js            # Card showing match details
    │   ├── MatchSmallCard.js             # Compact match card
    │   ├── Nav.js                        # Navigation bar
    │   ├── SearchFilters.js              # Match search/filter controls
    │   └── TeamCard.js                   # Team summary card
    └── pages/
        ├── TeamsPage.js                  # Home page - all teams
        ├── TeamSummaryPage.js            # Team details with recent matches
        └── MatchPage.js                  # Match listing with filters
```

### Running the Frontend

```bash
cd src/frontend
npm install
npm start
```

The app runs on [http://localhost:3000](http://localhost:3000). Configure the API URL in `src/frontend/.env`:

```
REACT_APP_DATA_API_URL=http://localhost:8081/api/v1
```

## Building for Production

Build the frontend and copy the output to the backend's static resources:

```bash
cd src/frontend
npm run build
cp -r build/* ../main/resources/public/
```

Then package the backend:

```bash
./mvnw clean package -DskipTests
```

## License

This project is for educational/demonstration purposes.
