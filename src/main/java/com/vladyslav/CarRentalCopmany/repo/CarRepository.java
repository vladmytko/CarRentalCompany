package com.vladyslav.CarRentalCopmany.repo;

import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCopmany.entity.enums.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.carAvailability = :status")
    List<Car> findCarByAvailability(AvailabilityStatus status);

    @Query("SELECT DISTINCT c.carType FROM Car c")
    List<CarType> findDistinctCarType();

    @Query("""
            SELECT c FROM Car c
            WHERE c.carAvailability = :status
            AND (:make IS NULL OR LOWER(c.make) = LOWER(:make))
            AND (:model IS NULL OR LOWER(c.model) = LOWER(:model))
            AND (:carType IS NULL OR LOWER(c.carType) = LOWER(:carType))
            AND (:numberOfSeats IS NULL OR c.numberOfSeats = :numberOfSeats)
            AND (:carPrice IS NULL OR c.carPrice <= :carPrice)
            AND c.id NOT IN (
                SELECT bk.car.id FROM Booking bk
                WHERE bk.checkInDate <= :checkOutDate
                AND bk.checkOutDate >= :checkInDate
            )
        """)
    List<Car> searchCarAdmin(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("make") String make,
            @Param("model") String model,
            @Param("carType") String carType,
            @Param("numberOfSeats") Integer numberOfSeats,
            @Param("carPrice") BigDecimal carPrice,
            @Param("status") AvailabilityStatus status
    );

    @Query("""
            SELECT c FROM Car c
            WHERE c.carAvailability = 'AVAILABLE'
            AND (:make IS NULL OR LOWER(c.make) = LOWER(:make))
            AND (:model IS NULL OR LOWER(c.model) = LOWER(:model))
            AND (:carType IS NULL OR LOWER(c.carType) = LOWER(:carType))
            AND (:numberOfSeats IS NULL OR c.numberOfSeats = :numberOfSeats)
            AND (:carPrice IS NULL OR c.carPrice <= :carPrice)
            AND c.id NOT IN (
                SELECT bk.car.id FROM Booking bk
                WHERE bk.checkInDate <= :checkOutDate
                AND bk.checkOutDate >= :checkInDate
            )
        """)
    List<Car> searchCar(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("make") String make,
            @Param("model") String model,
            @Param("carType") String carType,
            @Param("numberOfSeats") Integer numberOfSeats,
            @Param("carPrice") BigDecimal carPrice
    );

    boolean existsByRegistrationNumber(String registrationNumber);
}
