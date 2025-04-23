package com.vladyslav.CarRentalCopmany.service.impl;

import com.vladyslav.CarRentalCopmany.entity.Booking;
import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCopmany.repo.BookingRepository;
import com.vladyslav.CarRentalCopmany.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * This service contains scheduled tasks related to bookings,
 * such as updating car availability status after bookings end.
 */

// NEEDS UPDATE
@Service
public class BookingSchedulerService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    /**
     * Scheduled task that runs daily at 2:00AM to update the availability status of cars.
     * If a car has no future bookings after a completed booking, its status is set to AVAILABLE.
     */
    @Scheduled(cron = "0 0 2 * * ?") // Runs daily at 2:00AM
    public void updateCarAvailability() {
        try {
            // Find bookings that have already ended (check-out date before today)
            List<Booking> completedBookings = bookingRepository.findAllByCheckOutDateBefore(LocalDate.now());

            for (Booking booking : completedBookings) {
                Car car = booking.getCar();

                // Mark the car as available after current booking ends, regardless of future bookings
                car.setCarAvailability(AvailabilityStatus.AVAILABLE);
                carRepository.save(car);
            }

            System.out.println("Car availability update completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to update car availability: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
