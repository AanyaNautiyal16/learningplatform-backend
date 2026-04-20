# 🔐 Validation Annotations Guide

## Overview
Validation is now enforced at the **entity level** using Jakarta Bean Validation (JSR-303/JSR-380) annotations. Spring automatically validates `@RequestBody` parameters when `@Valid` is used in controllers.

---

## User Entity Validations

### 1. **@NotBlank** on `name` and `email`
```java
@NotBlank(message = "Name cannot be empty or contain only whitespace")
private String name;
```
**What it does:**
- Rejects empty strings, null values, and whitespace-only strings
- Applies trim before checking
- Returns custom error message on validation failure

**Example:**
```json
❌ POST /users
{ "name": "", "email": "john@example.com" }

✅ Response: 400 Bad Request
{ "fieldErrors": { "name": "Name cannot be empty or contain only whitespace" } }
```

---

### 2. **@Size** on `name` (2-100 characters)
```java
@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
private String name;
```
**What it does:**
- Enforces minimum length of 2 characters
- Enforces maximum length of 100 characters
- Prevents single-character names and extremely long names

**Examples:**
```json
❌ Too short
{ "name": "A", "email": "john@example.com" }

❌ Too long  
{ "name": "This is a very long name that exceeds the maximum limit...", "email": "john@example.com" }

✅ Valid
{ "name": "John Doe", "email": "john@example.com" }
```

---

### 3. **@Email** on `email`
```java
@Email(message = "Email should be valid (e.g., user@example.com)")
private String email;
```
**What it does:**
- Validates email format using RFC 5322 pattern
- Checks for required `@` and valid domain structure
- Rejects emails without proper format

**Examples:**
```json
❌ Invalid format
{ "name": "John", "email": "invalid-email" }
{ "name": "John", "email": "john@" }
{ "name": "John", "email": "@example.com" }

✅ Valid
{ "name": "John", "email": "john@example.com" }
{ "name": "John", "email": "john.doe@company.co.uk" }
```

---

### 4. **@Column(unique=true)** on `email` (Database Level)
```java
@Column(nullable = false, unique = true)
private String email;
```
**What it does:**
- Database constraint preventing duplicate emails
- Enforced at the database layer (additional safety)
- Throws `DataIntegrityViolationException` if violated

**Example:**
```json
❌ Second user with same email
POST /users
{ "name": "John2", "email": "john@example.com" }  ← Email already exists

✅ Response: 500 Internal Server Error (or custom error handler)
```

---

### 5. **@CreationTimestamp** and **@UpdateTimestamp**
```java
@CreationTimestamp
@Column(nullable = false, updatable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(nullable = false)
private LocalDateTime updatedAt;
```
**What they do:**
- `@CreationTimestamp`: Automatically set on record creation (never changes)
- `@UpdateTimestamp`: Automatically updated whenever the record is modified
- Useful for auditing and tracking changes

**Example Response:**
```json
GET /users/1
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2026-04-20T10:30:00",
  "updatedAt": "2026-04-20T14:45:00"
}
```

---

## Course Entity Validations

### 1. **@NotBlank** on `title`
```java
@NotBlank(message = "Course title cannot be empty or contain only whitespace")
private String title;
```
**Behavior:** Same as User name - rejects empty/null/whitespace-only strings

---

### 2. **@Size** on `title` (3-255 characters)
```java
@Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
private String title;
```
**What it does:**
- Minimum 3 characters (prevents "AI" or "ML" alone)
- Maximum 255 characters (database VARCHAR limit)

**Examples:**
```json
❌ Too short
{ "title": "AI", "description": "...", "price": 99.99, "user": {...} }

✅ Valid
{ "title": "Advanced AI Fundamentals", "description": "...", "price": 99.99, "user": {...} }
```

---

### 3. **@Size** on `description` (10-5000 characters, Optional)
```java
@Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 characters if provided")
private String description;
```
**What it does:**
- If provided, must be at least 10 characters
- Maximum 5000 characters (stored in LONGTEXT)
- **Null is allowed** (optional field)

**Examples:**
```json
❌ Empty string (not allowed if provided)
{ "title": "Course Title", "description": "", "price": 99.99, "user": {...} }

❌ Too short
{ "title": "Course Title", "description": "Short", "price": 99.99, "user": {...} }

✅ Null is OK (omit the field)
{ "title": "Course Title", "price": 99.99, "user": {...} }

✅ Valid
{ "title": "Course Title", "description": "This is a comprehensive course covering...", "price": 99.99, "user": {...} }
```

---

### 4. **@PositiveOrZero** on `price`
```java
@PositiveOrZero(message = "Price must be greater than or equal to 0")
private double price;
```
**What it does:**
- Accepts prices >= 0 (free courses allowed)
- Rejects negative prices
- Rejects null values (price is required)

**Examples:**
```json
❌ Negative price
{ "title": "Course", "price": -50.00, "user": {...} }

❌ Invalid type
{ "title": "Course", "price": "expensive", "user": {...} }

✅ Free course
{ "title": "Free Course", "price": 0.0, "user": {...} }

✅ Paid course
{ "title": "Premium Course", "price": 199.99, "user": {...} }
```

---

### 5. **@ManyToOne(optional=false)** on `user`
```java
@ManyToOne(optional = false)
@JoinColumn(name = "user_id", nullable = false)
private User user;
```
**What it does:**
- Course cannot exist without a User
- Enforced at JPA and database level
- Required foreign key

**Example:**
```json
❌ Missing user
POST /courses
{ "title": "Course", "description": "...", "price": 99.99 }

✅ Valid
POST /courses
{ 
  "title": "Course", 
  "description": "...", 
  "price": 99.99,
  "user": { "id": 1 }
}
```

---

## How Validation Works in Controllers

### With @Valid annotation
```java
@PostMapping
public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
}
```

**Flow:**
1. Client sends JSON request
2. Spring deserializes to `User` object
3. `@Valid` triggers all validations defined in User entity
4. If valid → method executes
5. If invalid → Spring returns `400 Bad Request` with error details

---

## Error Response Format (Automatic)

When validation fails, Spring provides error details:

```json
POST /users
{ "name": "", "email": "invalid-email" }

Response: 400 Bad Request
{
  "timestamp": "2026-04-20T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    {
      "objectName": "user",
      "field": "name",
      "rejectedValue": "",
      "message": "Name cannot be empty or contain only whitespace"
    },
    {
      "objectName": "user",
      "field": "email",
      "rejectedValue": "invalid-email",
      "message": "Email should be valid (e.g., user@example.com)"
    }
  ],
  "message": "Validation failed"
}
```

---

## Testing Validation

### Valid Request (should succeed)
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'

# Response: 201 Created
```

### Invalid Request (should fail)
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"","email":"not-an-email"}'

# Response: 400 Bad Request
```

---

## Database Constraints Enforced

| Constraint | Field | Effect |
|-----------|-------|--------|
| `NOT NULL` | name, email, title, price | Cannot insert null |
| `UNIQUE` | email | No duplicate emails |
| `NOT NULL, UNIQUE` | user_id in courses | Every course needs a user |
| `CHECK price >= 0` | price | Enforced by `@PositiveOrZero` |

---

## Summary

✅ **All validations are non-breaking** - field names and API structure unchanged  
✅ **Data quality improved** - invalid data never reaches database  
✅ **Clear error messages** - developers know exactly what's wrong  
✅ **Automatic enforcement** - no need to manually check in service layer  
✅ **Database + Application level** - defense in depth approach

No existing API contracts were changed. Tests and integrations continue to work as before, but now with stronger data validation!
