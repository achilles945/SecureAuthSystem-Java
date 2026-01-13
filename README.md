# 🔐 Secure Authentication System (Java)

A Core Java console application that demonstrates how real-world login systems work and how they defend against common attacks like brute force and SQL injection.

---

## ▶ Getting Started

1. Create the database and tables using the provided SQL.
2. Update DB credentials in `DBUtil.java`.
3. Add `mysql-connector.jar` to the classpath.
4. Compile and run `App.java`.

This is a console-based application.

---

## ✨ Features

### Normal Mode
- User Registration  
- User Login  
- Role-based access (USER / ADMIN)  
- Password hashing (no plain-text storage)  
- MySQL database integration using JDBC  

### Security Layer
- Tracks failed login attempts  
- Locks account after 3 consecutive failures  
- Prevents login for locked accounts  
- Logs every login attempt with timestamp  

### Attacker Mode (Simulation Only)
- **Brute Force Simulation**  
  Automatically tries multiple wrong passwords to show how account lock works.

- **SQL Injection Attempt**  
  Uses inputs like `' OR '1'='1` to demonstrate how `PreparedStatement` blocks SQL injection.

> Attacker Mode is for learning and demonstration only.

---

## 🧱 Tech Stack

- Java (Core Java)
- JDBC
- MySQL 
- Linux
- VS Code

---

## 📁 Project Structure

```

SecureAuthSystem/
├── src/
│   ├── App.java
│   ├── model/
│   │   └── User.java
│   ├── dao/
│   │   └── UserDAO.java
│   ├── service/
│   │   └── AuthService.java
│   └── util/
│       ├── DBUtil.java
│       └── HashUtil.java
├── lib/
│   └── mysql-connector.jar
└── README.md

```


---

## 🗄 Database Schema

```sql

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(20),
  failed_attempts INT DEFAULT 0,
  locked BOOLEAN DEFAULT FALSE
);

CREATE TABLE login_logs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  timestamp DATETIME,
  status VARCHAR(20)
);

```

---

## 🚀 How It Works

1. User registers → password is hashed → stored in DB

2. User logs in:

    - If account is locked → access denied

    - If password matches → success

    - If wrong → failed attempts increase

    - After 3 failures → account locked

3. Every attempt is logged in login_logs

4. Attacker Mode demonstrates:

    - Brute-force behavior

    - SQL injection attempts

    - How secure coding blocks them