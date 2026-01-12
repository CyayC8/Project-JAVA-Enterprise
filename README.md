#
# Cedric's Java Enterprise (Spring Boot + Vaadin) Project

This project is a Java enterprise-style web application built with Spring Boot and Vaadin (server-side UI). It demonstrates a full stack of enterprise concepts: authentication and authorization, a layered architecture (UI → service → repository), JPA/Hibernate persistence against an in-memory H2 database, messaging with JMS (ActiveMQ) for asynchronous email processing, and SMTP email delivery.

The application UI is a small “Pokéshop” portal where authenticated users can search their orders using multiple criteria, view details, and have the current search results emailed to them via a background queue.

Highlights
- Spring Boot application with WAR support for traditional servlet containers.
- Vaadin Flow UI (server-side, Java components), including a main layout, login, search, and detail views.
- JPA/Hibernate with an in-memory H2 database, preconfigured and auto-created on startup.
- Custom repository implementations using EntityManager (no Spring Data JPA repositories).
- Service layer with transactional boundaries.
- Spring Security with a custom UserDetailsService reading users from the database and a Vaadin-integrated login flow.
- JMS with ActiveMQ: UI enqueues an “orders overview” message; a listener consumes it and sends an HTML email via SMTP.


Project Structure (selected)
- src/main/java/be/ucll/SpringMain.java — Spring Boot entry point. Also supports WAR deployment via SpringBootServletInitializer.
- src/main/java/be/ucll/MyAppConfig.java — ComponentScan + configuration root.
- src/main/java/be/ucll/spring/JpaConfig.java — H2 TCP server, DataSource, JPA properties, EntityManagerFactory, and transaction manager.
- src/main/java/be/ucll/util/SecurityConfig.java — Spring Security config integrated with Vaadin’s security helpers and a custom UserDetailsService.
- src/main/java/be/ucll/util/H2IsolationLevelInitializerBean.java — Utility to set H2 isolation level on startup.
- src/main/java/be/ucll/setup/InitialDataSetup.java — (If present) seeds initial data; used to populate demo users/products/orders.
- src/main/java/be/ucll/repositories — Entities and repository interfaces/impls using EntityManager.
  - UserEntity, ProductEntity, OrderEntity (with ManyToOne/ManyToMany relations).
  - UserRepositoryImpl, ProductRepositoryImpl, OrderRepositoryImpl, TestRepositoryImpl.
- src/main/java/be/ucll/services — Service interfaces and @Service implementations (@Transactional where relevant).
- src/main/java/be/ucll/ui — Vaadin views: MainLayout, LoginView, SearchView, DetailView, TestView.
- src/main/java/be/ucll/jms — MailProducer (sender), MailListener (consumer), OrderEmailDTO, MailMessage classes.
- src/main/resources/application.properties — App configuration (port, Vaadin dev flags, ActiveMQ, SMTP, logging, etc.).
- src/main/resources/banner.txt — Custom Spring Boot banner.


Domain Model
- UserEntity: id, username, password, email. One-to-many with OrderEntity.
- ProductEntity: id, name, description, price. Many-to-many with OrderEntity.
- OrderEntity: id, user, products (EAGER for grid display), fields: totaalBedrag (total), aantalProducten, afgeleverd (delivered).


Layered Architecture
1) UI (Vaadin)
   - MainLayout: header, footer, logout button, and a content area where views are rendered.
   - LoginView: Vaadin login integrated with Spring Security via VaadinWebSecurity. Successful login redirects to /search.
   - SearchView: central screen to filter orders by min/max total, product count, product name, email, and delivered flag. Displays results in a Grid and provides a button to send the current results by email.
   - DetailView: navigates to /detail/{orderId} to show a single order (button from the grid).

2) Service Layer (be.ucll.services)
   - OrderService: findAll, findAllByUserId, searchOrders, findById.
   - ProductService: findAllProductNames (used to populate the product combo box in SearchView).
   - EmailService: sendEmail enqueues DTOs for asynchronous processing.

3) Repository Layer (be.ucll.repositories)
   - Uses EntityManager directly with HQL/JPQL queries in custom impl classes.
   - OrderRepositoryImpl: findAll, findAllByUserId, dynamic search (builds query based on provided criteria), findById.
   - ProductRepositoryImpl: fetches all product names.
   - UserRepositoryImpl: findAll and findByUsername (used by security and UI code).

4) Persistence & JPA Configuration
   - H2 in-memory DB, started as a TCP server (useful for external tools to connect while the app runs).
   - Hibernate DDL auto=create (schema is recreated each startup). Suitable for demos/dev only.
   - Entities are scanned under package be.ucll.

5) Security
   - VaadinWebSecurity base config. Static resources allowed. Login view: LoginView.
   - Custom UserDetailsService loads users from UserRepository and uses {noop} passwords (no hashing) for demo simplicity.
   - Logout mapped to GET /logout for easy use from a Vaadin Button. Clears auth, session, and cookies.

6) Messaging & Email
   - MailProducer uses Spring’s JmsTemplate to send List<OrderEmailDTO> to queue "mailQueue".
   - MailListener listens on "mailQueue", builds an HTML table of orders, and sends it using JavaMailSender (SMTP). The recipient is taken from the first DTO’s customerEmail.
   - OrderEmailDTO is the serializable data transfer object used on the queue.


How the main user flow works
1. User navigates to the app and sees the login page. After successful login, user is redirected to /search.
2. SearchView displays inputs and a grid. The product combobox is populated via ProductService.findAllProductNames().
3. On Search, the UI converts inputs to types (BigDecimal, Integer, Boolean) and calls OrderService.searchOrders(userId, ...). The query is dynamically built server-side in OrderRepositoryImpl.
4. The grid shows results. Clicking Details navigates to /detail/{orderId}.
5. Clicking E‑Mail collects current grid items, maps them to OrderEmailDTO, and calls EmailService.sendEmail, which enqueues the DTOs. MailListener consumes the message and sends an HTML email through SMTP.


Prerequisites
- Java 17+ (matching your project's Maven configuration)
- Maven 3.8+
- Node.js 18+ (for Vaadin/Vite frontend build, already integrated with Vaadin tooling)
- ActiveMQ broker running locally at tcp://localhost:61616 (default admin/admin). You can use Apache ActiveMQ Classic or Artemis configured for the same URL.
- SMTP account for sending emails (e.g., Gmail). Ensure “App Passwords” and “Less secure app access”/“2FA app password” is configured appropriately for your account.


Configuration (src/main/resources/application.properties)
- Server: server.port=${PORT:8080}
- Logging: logging.level.root=error
- Vaadin dev flags: vaadin.frontend.hotdeploy=true, vaadin.productionMode=false
- ActiveMQ: spring.activemq.broker-url, user, password, and packages.trust-all=true (for DTO serialization)
- Mail (SMTP): spring.mail.host, port, username, password, and TLS/auth properties

Important: Do not commit real credentials in version control for production. Use environment variables, externalized config (e.g., application-prod.properties), or a secrets manager. For local dev, you can override with environment variables or a local, untracked properties file.


Running the application (local dev)
1) Start ActiveMQ locally
   - Default broker URL: tcp://localhost:61616 (admin/admin). Adjust application.properties if different.

2) Ensure SMTP settings are valid
   - For Gmail, set an app password and use smtp.gmail.com:587 with STARTTLS.
   - Alternatively, use a local SMTP testing tool (e.g., MailHog, FakeSMTP) and point spring.mail.* to it.

3) Build and run
   - mvn clean spring-boot:run
   - Or build a WAR/JAR: mvn clean package

4) Open the app
   - http://localhost:8080 (login page). After login, you will be redirected to /search.

5) Log in
   - Initial users are loaded by the database seeding (see InitialDataSetup if present). Otherwise, create a user in the DB manually.


Troubleshooting
- ActiveMQ not running: You won’t see the “message placed on queue” effect, and no email will be sent. Start the broker or disable JMS/email paths for testing.
- SMTP errors: Check console logs from MailListener. Verify spring.mail.* settings and network/firewall constraints.
- H2 is in-memory: Data resets on app restart (by design). Use a persistent DB and update JpaConfig for persistence across restarts.
- Login issues: Ensure UserRepository has at least one user; passwords are {noop} (plain text) for demo. If using hashed passwords, adjust SecurityConfig accordingly.
- Vaadin frontend: If you face dev-mode issues, clear node_modules and do a fresh build. Ensure Node.js version is compatible with Vaadin/Vite.


Building and deploying as WAR
- The app extends SpringBootServletInitializer (SpringMain.configure) and can be packaged as a WAR (mvn package) for deployment to a servlet container (e.g., WildFly/Tomcat). VaadinServletContextInitializer bean is provided for WAR setups.


License
Internal/educational project. Add your preferred license here if needed.
