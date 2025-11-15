# Student Management REST API

A simple Student Management REST API built with Spring Boot that provides CRUD operations for managing student records.

## Technologies Used

- Spring Boot 3.5.7
- Spring Data JPA
- Spring Validation
- MySQL Database
- Lombok
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- XAMPP (for MySQL)
- Postman (for testing)

## Setup Steps

### 1. Database Setup

1. Start XAMPP and run MySQL server
2. Open phpMyAdmin (http://localhost/phpmyadmin)
3. Create a new database named `student_db`
4. The application will automatically create the tables

### 2. Configure Database Connection

Update `src/main/resources/application.properties` if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=
```

### 3. Run the Application

Using Maven Wrapper:
```bash
./mvnw spring-boot:run
```

Or using Maven:
```bash
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

## API Endpoints

**Base URL:** `http://localhost:8080/api/students`

### Core CRUD Operations

#### 1. Create Student
**Endpoint:** `POST /api/students`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

---

#### 2. Get All Students
**Endpoint:** `GET /api/students`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "course": "Information Technology",
    "age": 22
  }
]
```

---

#### 3. Get Student By ID
**Endpoint:** `GET /api/students/{id}`

**Example:** `GET /api/students/1`

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "course": "Computer Science",
  "age": 20
}
```

**Error Response:** `404 Not Found`
```json
{
  "timestamp": "2025-11-12T13:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id: 999",
  "path": "/api/students/999"
}
```

---

#### 4. Update Student
**Endpoint:** `PUT /api/students/{id}`

**Example:** `PUT /api/students/1`

**Request Body:**
```json
{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "course": "Software Engineering",
  "age": 21
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "course": "Software Engineering",
  "age": 21
}
```

---

#### 5. Delete Student
**Endpoint:** `DELETE /api/students/{id}`

**Example:** `DELETE /api/students/1`

**Response:** `200 OK`
```json
{
  "message": "Student deleted successfully",
  "id": "1"
}
```

---

### Bonus Features

#### 6. Get Paginated Students
**Endpoint:** `GET /api/students/paginated`

**Query Parameters:**
- `page` - Page number (0-based), default: 0
- `size` - Items per page, default: 10
- `sortBy` - Sort field (id, name, age, email, course), default: id
- `direction` - Sort direction (asc/desc), default: asc

**Examples:**

```bash
# Get first page with 5 items
GET /api/students/paginated?page=0&size=5

# Get second page with 5 items
GET /api/students/paginated?page=1&size=5

# Sort by name ascending
GET /api/students/paginated?page=0&size=10&sortBy=name&direction=asc

# Sort by age descending
GET /api/students/paginated?page=0&size=10&sortBy=age&direction=desc
```

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "course": "Computer Science",
      "age": 20
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 1,
  "totalElements": 5,
  "last": true,
  "first": true
}
```

---

#### 7. Search by Name
**Endpoint:** `GET /api/students/search/name`

**Query Parameters:**
- `name` - Name to search (case-insensitive, partial match)
- `page` - Page number, default: 0
- `size` - Items per page, default: 10

**Example:**
```bash
GET /api/students/search/name?name=John&page=0&size=10
```

**Response:** `200 OK` (Paginated results)

---

#### 8. Search by Course
**Endpoint:** `GET /api/students/search/course`

**Query Parameters:**
- `course` - Course to search (case-insensitive, partial match)
- `page` - Page number, default: 0
- `size` - Items per page, default: 10

**Example:**
```bash
GET /api/students/search/course?course=Computer&page=0&size=10
```

**Response:** `200 OK` (Paginated results)

---

#### 9. Search by Keyword (Name or Course)
**Endpoint:** `GET /api/students/search`

**Query Parameters:**
- `keyword` - Search keyword for name or course
- `page` - Page number, default: 0
- `size` - Items per page, default: 10
- `sortBy` - Sort field, default: id
- `direction` - Sort direction (asc/desc), default: asc

**Example:**
```bash
GET /api/students/search?keyword=software&page=0&size=10&sortBy=name&direction=asc
```

**Response:** `200 OK` (Paginated results)

## Validation Rules

- **Name:** Required, cannot be blank
- **Email:** Required, must be valid email format
- **Course:** Required, cannot be blank
- **Age:** Required, must be greater than 18

### Test Validation Errors

#### Invalid Email Format
**Request:**
```json
{
  "name": "Test User",
  "email": "invalid-email",
  "course": "Computer Science",
  "age": 20
}
```

**Response:** `400 Bad Request`
```json
{
  "timestamp": "2025-11-12T13:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/students",
  "validationErrors": {
    "email": "Email should be valid"
  }
}
```

#### Age Less Than 18
**Request:**
```json
{
  "name": "Test User",
  "email": "test@example.com",
  "course": "Computer Science",
  "age": 17
}
```

**Response:** `400 Bad Request`
```json
{
  "timestamp": "2025-11-12T13:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/students",
  "validationErrors": {
    "age": "Age must be greater than 18"
  }
}
```


## Testing with Postman

### Manual Testing Steps

**Base URL:** `http://localhost:8080/api/students`

#### Core CRUD Operations
1. **Create Student** - `POST /api/students`
2. **Get All Students** - `GET /api/students`
3. **Get Student By ID** - `GET /api/students/1`
4. **Update Student** - `PUT /api/students/1`
5. **Delete Student** - `DELETE /api/students/1`

#### Test Error Scenarios
6. **Invalid Email** - `POST /api/students` (with invalid email)
7. **Age < 18** - `POST /api/students` (with age 17)
8. **Student Not Found** - `GET /api/students/999`

#### Bonus Features
9. **Paginated Students** - `GET /api/students/paginated?page=0&size=5`
10. **Sort by Name** - `GET /api/students/paginated?sortBy=name&direction=asc`
11. **Sort by Age** - `GET /api/students/paginated?sortBy=age&direction=desc`
12. **Search by Name** - `GET /api/students/search/name?name=John`
13. **Search by Course** - `GET /api/students/search/course?course=Computer`
14. **Search by Keyword** - `GET /api/students/search?keyword=software`

