# Spring Boot Finance Tracker

A finance tracker application created as an exercise to learn Spring Boot for Java

## Features

- **Core Functionality:** Primary feature description.
- **Performance:** Fast processing or low-memory footprint.
- **Integration:** Easy configuration via environment variables or properties files.

## Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK):** Version 17 or higher (Recommended: OpenJDK 21)
- **Build Tool:** Maven (3.8+) OR Gradle (8.0+)
- **Git:** To clone the repository

## Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com
   cd your-repo-name
   ```

2. **Build the application:**

   ```bash
   ./mvnw clean package
   ```

This will compile the code and generate an executable `.jar` file inside the `target/` directory.

## Usage

### Running the Application

You can execute the compiled JAR file directly using the `java -jar` command:

```bash
# For Maven builds
java -jar target/finance-tracker-1.0.0.jar

```

### Configuration (Optional)

If your application requires specific environment variables or external configuration properties:

```bash
export APP_PORT=8080
java -jar target/finance-tracker-1.0.0.jar
```

## Running Tests

To execute the test suite (JUnit/TestNG), run:

*For Maven:*
```bash
./mvnw test
```

## Built With

- [Java](https://www.oracle.com/java/) - Programming language
- [Maven](https://apache.org) / [Gradle](https://gradle.org) - Build management
- [Spring Boot](https://spring.io) 

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Project Structure

Structure for source files contained in `src/main/java/`
```
com.marknoblin.financetracker
│
├── FinanceTrackerApplication.java         # Root class
│
├── config/                    # Global security, CORS, bean definitions [0.6.11, 0.6.13]
├── controller/                # REST endpoints (@RestController) [0.6.2, 0.6.13]
├── dto/                       # Request/Response payloads (Java Records) [0.6.2, 0.6.13]
├── entity/                    # Database models (@Entity) [0.6.5, 0.6.13]
├── exception/                 # Global exception handlers (@ControllerAdvice) [0.6.5, 0.6.13]
├── repository/                # Spring Data DB access interfaces [0.6.2, 0.6.13]
└── service/                   # Core business logic [0.6.2, 0.6.13]
```

Configuration structure contained in `src/main/resources/`
```
resources
├──application.yml             # Base application configuration
├──application-dev.yml         # Configuration values for development profile
└──application-prod.yml        # Configuration values for production profile
```
