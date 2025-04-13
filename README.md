
# 📦 Product Discount Management System

A Spring Boot application that calculates discounts on products based on various rules such as flat discount, percentage discount, and optional seasonal discounts. The application uses PostgreSQL as the database and supports secure API access via basic authentication.

---

Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Security (Basic Auth)
- PostgreSQL

---

How to Run the Application

### 1. Clone the repository

```bash
git clone https://github.com/your-repo/discount-system.git
cd discount-system
```

### 2. Configure Database

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/discount_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Security
spring.security.user.name=admin
spring.security.user.password=admin123
```

### 3. Build and Run

```bash
./mvnw spring-boot:run
```

> Make sure PostgreSQL is running and the database `discount_db` exists.

---

## API Authentication

All APIs are protected with **Basic Auth**.

**Username:** `admin`  
**Password:** `admin123`

---

## API Endpoint

### **POST** `/product/v1/discount`

#### Request Headers:
```
Authorization: Basic YWRtaW46YWRtaW4xMjM=  // admin:admin123 (Base64)
Content-Type: application/json
```

#### Request Body (Example):

```json
{
  "productId": "2",
  "discountType": "flat",
  "discountValue": 300,
  "seasonalDiscountActive": true,
  "quantity": 1
}
```

> ✅ This will calculate the discount based on actual product data in the database. `productId`, `discountType`, `discountValue`, `seasonalDiscountActive`, and `quantity` are **required**.

#### Response (Example):

```json
{
  "productId": "2",
  "originalPrice": 500.0,
  "finalPrice": 0.0,
  "discountApplied": true,
  "message": "Flat and seasonal discounts applied. Price capped to zero."
}
```

---

## Error Handling

| Condition                | HTTP Status | Message                          |
|--------------------------|-------------|----------------------------------|
| Product not found        | 404         | "Product not found with id: ..." |
| Invalid discount type    | 400         | "Invalid discount type: ..."     |
| Product out of stock     | 400         | "Product is out of stock."       |
| Discount exceeds price   | 200         | Final price = 0, message shown   |

---

## Directory Structure

```
src/main/java/com/example/discountapp
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
├── service/
└── DemoApplication.java
```

---

## Notes

- The discount is always calculated using the **latest product price and quantity** from the database (not from request).
- Seasonal discount is applied **only if today's date is within the season date range**.
- Use the `Season` entity to define valid seasonal windows.

---

## Test the API Using Postman

1. Set method to `POST`
2. URL: `http://localhost:8080/product/discount`
3. Add **Basic Auth**:
   - Username: `admin`
   - Password: `admin123`
4. Go to **Body > raw > JSON** and paste the example JSON.

---

## ✅ Author

Created by Mahemudhasan Vadhaniya — Spring Boot Developer  
