# Student-to-Student Skill Swap Portal

A complete, production-ready **MVC Web Application** built in Java, designed to help students connect and exchange skills (e.g., "I'll teach you Java if you teach me Photoshop"). 

This project was strictly built **without** external frameworks like Spring Boot or Hibernate to demonstrate a profound understanding of core Java EE, Servlets, JSP, JDBC, and SQL schemas. It is perfectly tailored for a Fresher/Junior Developer portfolio.

## 🚀 Features

*   **Role-Based Access Control:** Secure `STUDENT` and `ADMIN` environments.
*   **Authentication System:** Secure login/registration with `SHA-256` password hashing and Session Management.
*   **Skill Management:** Students can dynamically build their profile by adding skills they *Teach* and skills they *Want to Learn*.
*   **Advanced Search & Match Algorithm:** A Java-based matching engine that cross-references what a user teaches with what another user wants, flagging them as a "🎯 Perfect Match" or "🤝 Potential Match".
*   **Swap Request Engine:** Two-sided bargaining system allowing students to send, accept, and reject swap proposals.
*   **Active Exchanges & Chat:** Accepting a request initiates a secure JDBC transaction to create an active learning exchange. Features an integrated WhatsApp-style messaging system.
*   **Ratings & Reviews:** Post-exchange feedback system with a CSS-only interactive star rating UI.
*   **Notification System:** Alerts students to new requests and accepted swaps using dynamic read-receipt logic.
*   **Admin Analytics:** A dedicated dashboard for administrators to monitor total users, active exchanges, and completed swaps using optimized SQL aggregate functions.

## 🛠 Tech Stack

*   **Backend:** Java 8+, Servlets 4.0, JSP (JavaServer Pages)
*   **Frontend:** HTML5, Vanilla CSS3 (Flexbox/Grid), JSTL
*   **Database:** MySQL 8.0
*   **Data Access:** JDBC (Raw PreparedStatements, Transaction Management)
*   **Build Tool:** Maven
*   **Server:** Apache Tomcat 9+

## ⚙️ Architecture (Strict MVC)

*   **Model (`com.skillswap.dto`):** Plain Old Java Objects representing database entities.
*   **Data Access (`com.skillswap.dao`):** Interfaces and Implementations isolating all raw SQL queries, `ResultSet` parsing, and `Connection` handling.
*   **Service (`com.skillswap.service`):** The business logic layer. It enforces rules (e.g., "no duplicate requests", "rating must be 1-5") before calling the DAO.
*   **Controller (`com.skillswap.controller`):** Java Servlets that intercept HTTP requests, validate sessions, invoke Services, and route data via the POST-Redirect-GET pattern.
*   **View (`/jsp`):** Secure JSP pages using JSTL tags and modular `<%@ include %>` components to dynamically render data without embedding business logic.

## 💾 Installation & Setup

### 1. Database Configuration
1. Open MySQL Workbench (or your preferred SQL client).
2. Execute the entire SQL script located at `database/database.sql`. This will automatically create the `skillswap_db` schema, build all tables, and insert default skills and an Admin account.
3. Open `src/main/java/com/skillswap/util/DBConstants.java` and ensure `DB_USER` and `DB_PASSWORD` match your local MySQL credentials.

### 2. Building the Application
1. Ensure Maven is installed on your system.
2. Navigate to the project root directory in your terminal.
3. Run the following command to download dependencies and build the WAR file:
   ```bash
   mvn clean install
   ```

### 3. Deployment
1. Copy the generated `skillswapportal.war` file from the `target/` directory.
2. Paste it into the `webapps/` folder of your Apache Tomcat installation.
3. Start Tomcat (`bin/startup.bat` on Windows or `sh bin/startup.sh` on Mac/Linux).
4. Access the application in your browser at:
   ```
   http://localhost:8080/skillswapportal/jsp/auth/login.jsp
   ```

## 🔐 Default Credentials
*   **Admin Login:** `admin@skillswap.com` / `admin123`
*   **Student Login:** Register a new account via the UI!
