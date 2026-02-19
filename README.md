# Ultra-Fast-Pagination

```markdown
# 🚀 Ultra-Fast Pagination in Spring Boot  
### Offset vs Keyset (Cursor-Based) Pagination – Performance Comparison

This project demonstrates and compares two pagination strategies in Spring Boot using MySQL:

- ✅ Offset-Based Pagination (Traditional)
- ✅ Keyset (Cursor-Based) Pagination (Production-Ready & High Performance)

The goal is to understand performance differences and identify which approach is suitable for large datasets in real-world production systems.

---

## 📌 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL 8
- Swagger (OpenAPI)
- Maven

---

## 🧠 Problem Statement

Offset pagination works well for small datasets but becomes slow when:

- Page numbers increase
- Dataset grows large (10k+ records)
- Queries use ORDER BY with OFFSET

Keyset pagination solves this by using indexed column values instead of OFFSET.

This project compares both approaches side-by-side.

---

# 🔎 Pagination Approaches

---

## 1️⃣ Offset-Based Pagination

### Endpoint
```

GET /offset/orders?page=0&size=20

````

### How It Works

Uses:
```sql
LIMIT 20 OFFSET 40000
````

Database must:

* Scan rows
* Skip previous rows
* Then return required records

⚠️ Performance degrades as page number increases.

---

## 2️⃣ Keyset (Cursor-Based) Pagination

### Endpoint

```
GET /keyset/orders?cursor=<encoded_value>&size=20
```

### How It Works

Uses:

```sql
WHERE (created_at, id) < (?, ?)
ORDER BY created_at DESC, id DESC
LIMIT 20
```

Instead of skipping rows, it:

* Uses last record values as reference
* Uses indexed columns
* Fetches next records directly

✅ Much faster for large datasets
✅ Production-ready approach

---

# 📊 Performance Measurement

Execution time is added in response headers:

```
X-Execution-Time: 45ms
```

You can compare:

* Offset pagination with large page numbers
* Keyset pagination with same dataset size

Swagger UI shows response headers.

---

# 🏗 Project Structure

```
org.example.paginationcomparison
│
├── controller
│   ├── OffsetOrderController
│   └── KeysetOrderController
│
├── service
│   ├── OffsetOrderService
│   └── KeysetOrderService
│
├── repository
│   ├── OrderRepository
│   └── KeysetOrderRepository
│
├── entity
│   └── Order
│
├── cursor
│   ├── CursorUtil
│   └── OrderCursor
│
└── DataLoader (inserts test data)
```

---

# 🛠 How to Run the Project

### 1️⃣ Start MySQL

Make sure MySQL is running and create database:

```sql
CREATE DATABASE pagination_db;
```

---

### 2️⃣ Configure application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/pagination_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3️⃣ Run the Application

In IntelliJ:

```
Run PaginationComparisonApplication
```

Server starts at:

```
http://localhost:8080
```

---

# 📘 Swagger UI

Access API documentation:

```
http://localhost:8080/swagger-ui/index.html
```

You can test:

* Offset pagination
* Keyset pagination
* Compare execution time

---

# 📈 Testing Large Dataset

The project inserts test records using a `DataLoader`.

You can modify record count inside:

```
DataLoader.java
```

Example:

```java
for (int i = 0; i < 10000; i++)
```

---

# 🎯 When To Use What?

| Scenario                           | Use Offset | Use Keyset |
| ---------------------------------- | ---------- | ---------- |
| Small dataset                      | ✅          | ✅          |
| Large dataset                      | ❌          | ✅          |
| Infinite scrolling                 | ❌          | ✅          |
| Jump to random page                | ✅          | ❌          |
| Production high-performance system | ❌          | ✅          |

---


# 📌 Conclusion

Offset pagination is simple but inefficient for large datasets.

Keyset pagination:

* Scales better
* Uses indexes properly
* Is the preferred approach in high-performance systems

This project provides a practical comparison for learning and production implementation.

---



Just tell me what you want next 💪
