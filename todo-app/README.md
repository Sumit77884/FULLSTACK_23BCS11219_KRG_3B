# 📝 Todo CRUD Application — Spring Boot + MongoDB

A backend RESTful CRUD application for managing Todo tasks, built with **Spring Boot 3** and **MongoDB**.

---

## 🏗️ Project Structure

```
todo-app/
├── pom.xml
└── src/
    └── main/
        ├── java/com/example/todo/
        │   ├── TodoApplication.java         ← Entry point
        │   ├── controller/
        │   │   └── TodoController.java      ← REST API layer
        │   ├── service/
        │   │   ├── TodoService.java         ← Service interface
        │   │   └── TodoServiceImpl.java     ← Business logic
        │   ├── repository/
        │   │   └── TodoRepository.java      ← MongoDB repository
        │   ├── model/
        │   │   └── Todo.java               ← Entity/Schema
        │   └── exception/
        │       ├── ResourceNotFoundException.java
        │       ├── DuplicateResourceException.java
        │       └── GlobalExceptionHandler.java
        └── resources/
            └── application.properties
```

---

## 🗄️ Todo Schema

| Field    | Type    | Required | Default |
|----------|---------|----------|---------|
| id       | String  | Auto     | ObjectId (MongoDB) |
| taskId   | Integer | ✅ Yes   | —       |
| title    | String  | ✅ Yes   | —       |
| status   | Boolean | No       | `false` |

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.6+
- MongoDB running on `localhost:27017`

---

## 🚀 Running the Application

```bash
# 1. Start MongoDB
mongod --dbpath /data/db

# 2. Clone / unzip the project
cd todo-app

# 3. Build and run
mvn spring-boot:run
```

The server starts on **http://localhost:8080**

---

## 🔗 API Endpoints

### 1. Create a Todo
**POST** `/api/createTodo`

**Request Body:**
```json
{
  "taskId": 1,
  "title": "Buy groceries",
  "status": false
}
```

**Response (201 Created):**
```json
{
  "id": "64f1a2b3c4d5e6f7a8b9c0d1",
  "taskId": 1,
  "title": "Buy groceries",
  "status": false
}
```

---

### 2. Get All Todos
**GET** `/api/getAll`

**Response (200 OK):**
```json
[
  {
    "id": "64f1a2b3c4d5e6f7a8b9c0d1",
    "taskId": 1,
    "title": "Buy groceries",
    "status": false
  },
  {
    "id": "64f1a2b3c4d5e6f7a8b9c0d2",
    "taskId": 2,
    "title": "Read a book",
    "status": true
  }
]
```

---

### 3. Get Task by ID
**GET** `/api/getTask/{id}`

Example: `GET /api/getTask/1`

**Response (200 OK):**
```json
{
  "id": "64f1a2b3c4d5e6f7a8b9c0d1",
  "taskId": 1,
  "title": "Buy groceries",
  "status": false
}
```

---

### 4. Update Task
**PUT** `/api/updateTask/{id}`

Example: `PUT /api/updateTask/1`

**Request Body:**
```json
{
  "title": "Buy groceries and cook dinner",
  "status": true
}
```

**Response (200 OK):**
```json
{
  "id": "64f1a2b3c4d5e6f7a8b9c0d1",
  "taskId": 1,
  "title": "Buy groceries and cook dinner",
  "status": true
}
```

---

### 5. Delete Task
**DELETE** `/api/deleteTask/{id}`

Example: `DELETE /api/deleteTask/1`

**Response (200 OK):**
```
Todo with taskId 1 deleted successfully.
```

---

## ❌ Error Responses

| Scenario              | Status | Example Message                             |
|-----------------------|--------|---------------------------------------------|
| Task not found        | 404    | `Todo not found with taskId: 99`            |
| Duplicate taskId      | 409    | `Todo with taskId 1 already exists.`        |
| Validation failure    | 400    | `{ "taskId": "taskId is required" }`        |
| Server error          | 500    | `An unexpected error occurred: ...`         |

---

## 🧪 Testing with cURL

```bash
# Create
curl -X POST http://localhost:8080/api/createTodo \
  -H "Content-Type: application/json" \
  -d '{"taskId":1,"title":"Learn Spring Boot","status":false}'

# Get All
curl http://localhost:8080/api/getAll

# Get by ID
curl http://localhost:8080/api/getTask/1

# Update
curl -X PUT http://localhost:8080/api/updateTask/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot & MongoDB","status":true}'

# Delete
curl -X DELETE http://localhost:8080/api/deleteTask/1
```

---

## 🛠️ Tech Stack

| Layer        | Technology              |
|-------------|-------------------------|
| Language     | Java 17                |
| Framework    | Spring Boot 3.2         |
| Database     | MongoDB                 |
| ORM          | Spring Data MongoDB     |
| Build Tool   | Maven                   |
| Utilities    | Lombok                  |
