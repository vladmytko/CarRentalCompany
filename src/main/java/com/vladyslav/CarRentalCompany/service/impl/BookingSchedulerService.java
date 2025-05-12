package com.vladyslav.CarRentalCompany.service.impl;

import com.vladyslav.CarRentalCompany.entity.Booking;
import com.vladyslav.CarRentalCompany.entity.Car;
import com.vladyslav.CarRentalCompany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCompany.repo.BookingRepository;
import com.vladyslav.CarRentalCompany.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * This service contains scheduled tasks related to bookings,
 * such as updating car availability status after bookings end.
 */
@Service
public class BookingSchedulerService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    public CarRepository carRepository;

    /**
     * Scheduled task that runs daily at 2:00AM to update the availability status of cars.
     * If a car has an active booking today, its status is set to RENTED.
     * Otherwise, it is set to AVAILABLE.
     */
    @Scheduled(cron = "0 0 2 * * ?") // Runs daily at 2:00AM
    public void updateAllCarAvailability() {
        try {
            List<Car> cars = carRepository.findAll();

            for (Car car : cars) {
                updateCarAvailabilityBasedOnBookings(car);
            }

            System.out.println("Car availability update completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to update car availability: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the availability status of a given car based on today's date and its bookings.
     * @param car the car whose availability is to be updated
     */
    public void updateCarAvailabilityBasedOnBookings(Car car) {
        LocalDate today = LocalDate.now();

        boolean isCurrentlyRented = car.getBookings().stream()
                .anyMatch(booking ->
                        (today.isEqual(booking.getCheckInDate()) || today.isAfter(booking.getCheckInDate())) &&
                        today.isBefore(booking.getCheckOutDate().plusDays(1))
                );

        car.setCarAvailability(isCurrentlyRented ? AvailabilityStatus.RENTED : AvailabilityStatus.AVAILABLE);
        carRepository.save(car);
    }
}
