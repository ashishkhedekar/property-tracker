# Property-Tracker

Running application locally

1. Clone the git repository 
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

   # Using profile dev   
   ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
   ```
   
## Packaging
1. Create docker image
   ```
    docker build -t property-tracker-spring-image .
   ```
2. Run Application with database using docker-compose
   ```
   docker-compose up -d
   ```
   
## Functionality:
1. To fetch property updates for all the existing locations
   ```
   GET http://localhost:8080/schedule
   ```

2. Scheduler `generateLocationStats` runs every minute and generates stats, saved in table `location_market_stats`