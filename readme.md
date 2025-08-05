# GenericCRUD

A Spring Boot application that provides a generic CRUD (Create, Read, Update, Delete) system for microservices architecture. This project aims to make microservices as generic as possible to accelerate contract changes at runtime and simplify the creation of new microservices.

## 🎯 Objective

The main goal is to create a generic system where relay and consumer components always have the same structure, with only queue names and contracts changing. This approach explores both sides to make them generic, and for the consumer, it handles which endpoint to send data to the service.

## 🚀 Features

- **Generic CRUD Operations**: Universal create, read, update, and delete operations
- **Dynamic Contract Management**: Runtime contract changes and endpoint modifications
- **In-Memory Object Definitions**: Fast data insertion with object definitions kept in memory
- **HashMap-based Object Generalization**: Flexible object handling using Java HashMap
- **MongoDB Integration**: Persistent storage with MongoDB
- **Spring Boot Framework**: Modern Java application framework
- **Lombok Integration**: Reduced boilerplate code
- **Validation Support**: Input validation with Spring Boot validation

## 🏗️ Architecture

### Core Components

- **MyObject**: Used for passing instantiated objects
- **MyClass**: Used for defining name, type, and null constraints for each field
- **Static Memory Storage**: All MyObject and MyClass instances are loaded into memory on startup
- **File-based Storage Option**: Alternative to database for small datasets

### AssetsServices Structure

The system supports a JSON-based configuration structure:

```json
[
  {
    "id": "main",
    "params": [
      {
        "id": "",
        "key": "",
        "value": ""
      }
    ]
  },
  {
    "id": "chat",
    "params": [
      {
        "id": "",
        "key": "",
        "value": ""
      }
    ]
  }
]
```

- **Public JSON**: Accessible configuration
- **Private JSON**: Token-protected configuration for application parameterization

## 🛠️ Technology Stack

- **Java 11**
- **Spring Boot 2.5.4**
- **MongoDB** (Spring Data MongoDB)
- **Lombok**
- **Joda Time 2.10**
- **Spring Boot Validation**

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6+
- MongoDB instance (local or remote)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd GenericCRUD
```

### 2. Configure MongoDB

Ensure MongoDB is running and accessible. Update the MongoDB connection settings in `application.properties` or `application.yml` if needed.

### 3. Build the Project

```bash
./mvnw clean install
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

Or using the Maven wrapper:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📁 Project Structure

```
src/main/java/com/gunter/genericcrud/
├── controller/     # REST API controllers
├── domain/        # Entity and model classes
├── exception/     # Custom exception handlers
├── repository/    # Data access layer
├── service/       # Business logic layer
└── GenericCrudApplication.java  # Main application class
```

## 🔧 Configuration

### Application Properties

Create or update `src/main/resources/application.properties`:

```properties
# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=genericcrud

# Server Configuration
server.port=8080

# Logging
logging.level.com.gunter.genericcrud=DEBUG
```

## 📚 API Usage

### Basic CRUD Operations

The application provides generic endpoints for CRUD operations:

- `POST /api/objects` - Create new object
- `GET /api/objects` - Retrieve all objects
- `GET /api/objects/{id}` - Retrieve object by ID
- `PUT /api/objects/{id}` - Update object
- `DELETE /api/objects/{id}` - Delete object

### Dynamic Contract Management

Contracts can be modified at runtime using the configuration endpoints.

## 🔄 Development

### Adding New Features

1. Create new domain classes in the `domain` package
2. Add corresponding repository interfaces in the `repository` package
3. Implement business logic in the `service` package
4. Create REST controllers in the `controller` package

### Testing

Run tests using:

```bash
./mvnw test
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions, please open an issue in the repository or contact the development team.

---

**Note**: This system is designed for microservices architecture where relay and consumer components share the same structure, with only queue names and contracts varying. The generic approach allows for rapid development and deployment of new microservices.
