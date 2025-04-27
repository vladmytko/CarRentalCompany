# 🚗 Car Rental Company - Spring Boot Application

This is a **Car Rental Management System** built using **Spring Boot**.  
It allows users to browse available cars, make bookings, and manage their reservations.

---

## ✨ Features

- **User Management**:
    - Register and manage users (admin and customer roles).

- **Car Management**:
    - View cars and vans available for rental.
    - Check car availability based on booking dates.

- **Booking System**:
    - Create a new booking with check-in and check-out dates.
    - Calculate total price automatically based on rental duration and car price.
    - Validate total number of people against available seats in the car.
    - Generate random booking confirmation codes.
    - Cancel and update existing bookings.
    - Find bookings by confirmation code, user ID, or car ID.

- **Validation and Error Handling**:
    - Custom exceptions (e.g., car not available, booking not found, etc.).
    - Proper validation of booking dates (check-out must be after check-in).

---

## ⚙️ Technologies Used

- Java 21
- Spring Boot 3
- Spring Data JPA (Hibernate)
- MySQL Database
- Lombok
- Maven
- Hibernate Validator
- JWT (planned for security, based on dependencies)

---

## 🛠️ Project Structure

- **CarRentalCompany/**
    - **src/main/java/com/vladyslav/CarRentalCompany/**
        - **controller/** – Handles HTTP requests (booking, user, car APIs)
        - **dto/** – Data Transfer Objects (BookingDTO, CarDTO, UserDTO, VanDTO)
        - **entity/** – JPA Entities (Booking, Car, User, Van, enums like AvailabilityStatus)
        - **repo/** – Spring Data Repositories (BookingRepository, CarRepository, UserRepository)
        - **service/**
            - **interfac/** – Service interfaces (e.g., IBookingService)
            - **impl/** – Service implementations (e.g., BookingService)
        - **utils/** – Utility classes (e.g., Utils.java for mapping and helpers)
        - **exception/** – Custom exceptions (e.g., OurException)
    - **src/main/resources/**
        - **application.properties** – Application configuration
        - **static/** – Static resources (optional, for frontend files if needed)
    - **src/test/java/com/vladyslav/CarRentalCompany/** – Unit and Integration Tests
- **pom.xml** – Maven build file
- **README.md** – Project documentation
---

## 🛡️ How Booking Works

1. **User selects a car** and booking dates (check-in and check-out).
2. **System checks**:
    - The car is available during the date range.
    - The number of people fits the car’s seating capacity.
3. **Booking is saved** with:
    - Total number of people calculated automatically.
    - Total price based on number of rental days and car price.
    - Random confirmation code generated.
4. **User receives** booking confirmation and details.

---

## 🛠️ Scheduled Car Checks

The system includes a **Scheduled Task** that **automatically checks cars** for overdue maintenance or availability updates.

- Runs **periodically** using Spring’s `@Scheduled` annotation.
- Checks each car’s data (e.g., availability, booking conflicts, maintenance schedules).
- Updates car status in the database if necessary (e.g., marks cars as unavailable if past due for maintenance).
- Improves system reliability by ensuring cars are always properly managed without manual admin intervention.

### 🔍 How It Works
- The scheduler runs at a **fixed interval** (for example: every day at midnight).
- It queries all cars in the database.
- It applies business rules:
    - If a car is unavailable beyond a certain period → mark it for maintenance.
    - If a booking is outdated → reset the car to `AVAILABLE`.
- Saves updated car statuses back into the database.

---

## 🧪 Tests for Scheduled Car Checks

Unit tests and integration tests were created to **verify the scheduled car check logic**:

- **Unit Tests**:
    - Test individual service methods used by the scheduler.
    - Mock repositories to verify logic without touching the real database.
    - Check that:
        - Cars needing maintenance are correctly flagged.
        - Cars with expired bookings are made available again.

- **Integration Tests**:
    - Load the full application context.
    - Run the scheduled task manually inside tests.
    - Verify that car statuses are correctly updated in a real database environment (H2 database for testing).

### 📄 Tests
- **Given**: A car has an old booking or overdue maintenance.
- **When**: The scheduled task runs.
- **Then**: The car is updated correctly to `AVAILABLE` or flagged for maintenance.

---

✅ Scheduled tasks and their tests ensure that the car fleet is **automatically managed**, **consistent**, and **ready for customers**.

## 🔥 How to Run Locally

1. **Clone the repository**:

```bash
git clone https://github.com/yourusername/car-rental-company.git
cd car-rental-company 
```

## 🛡️ Set up MySQL database:

1. Create a database named car_rental_company.
2. Update application.properties with your DB credentials

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/car_rental_company
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```
