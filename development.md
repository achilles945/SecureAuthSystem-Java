# Development Notes

# File-by-File Architecture & Roles

## App.java (Entry Point / UI Layer)
- Contains the `main()` method.
- Displays the menu:
  - Register
  - Login
  - Attacker Mode
  - Exit
- Accepts user input from the console.
- Calls methods from `AuthService`.
- Does **not** directly interact with the database.

Flow:
```
App.java → AuthService.java

```

---

## AuthService.java (Business Logic Layer)
- Acts as the brain of the application.
- Handles:
  - User registration logic
  - Login validation
  - Failed-attempt tracking
  - Account lockout logic
  - Attacker mode simulation
- Uses:
  - `HashUtil` for password hashing and verification
  - `UserDAO` for all database operations
- Works with `User` objects returned from DAO.

Flow:
```
App.java → AuthService.java → UserDAO.java
↓
HashUtil.java

```

---

## UserDAO.java (Data Access Layer)
- Responsible only for database operations.
- Contains methods like:
  - `saveUser(User user)`
  - `findUserByUsername(String username)`
  - `updateFailedAttempts(...)`
  - `lockAccount(...)`
  - `logAttempt(...)`
- Uses `DBUtil` to obtain JDBC connections.
- Converts database rows into `User` objects.

Flow:
```
AuthService.java → UserDAO.java → DBUtil.java → Database

```

---

## User.java (Model / POJO)
- Represents a user entity in the system.
- Fields:
  - id
  - username
  - passwordHash
  - role
  - failedAttempts
  - locked
- Used by:
  - `UserDAO` to map DB data
  - `AuthService` to apply business rules

Acts as a bridge between Service and DAO layers.

---

## DBUtil.java (Utility)
- Manages JDBC connection setup.
- Stores:
  - Database URL
  - Username
  - Password
- Provides a `getConnection()` method.
- Used only by `UserDAO`.

---

## HashUtil.java (Utility)
- Handles password security.
- Provides methods such as:
  - `hashPassword(String password)`
  - `verifyPassword(String input, String storedHash)`
- Used by `AuthService` during:
  - Registration
  - Login verification

---

## Overall Flow

```
App.java
↓
AuthService.java
↓
UserDAO.java
↓
Database

```

Utilities:

- AuthService.java → HashUtil.java

- UserDAO.java → DBUtil.java

- UserDAO.java & AuthService.java → User.java
