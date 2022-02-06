# property-tracker

This is a property tracker app

Running this application locally

1. Clone the repository 
   ```
   git clone https://github.com/ashishkhedekar/property-tracker.git
   ```
2. Create database, user and grant access rights
   ```
   > mysql -u root
   mysql> CREATE DATABASE property_tracker;
   mysql> CREATE USER 'property_tracker'@'localhost' IDENTIFIED BY 'password';
   mysql> GRANT ALL PRIVILEGES ON property_tracker.* TO 'property_tracker'@'localhost';
   ```
3. Build project 
   ```
   mvn clean install
   ```
4. Start the server
   ```
   ./mvnw spring-boot:run 
   ```