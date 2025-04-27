package com.vladyslav.CarRentalCopmany;

import com.vladyslav.CarRentalCopmany.entity.Booking;
import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCopmany.repo.CarRepository;
import com.vladyslav.CarRentalCopmany.service.impl.BookingSchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookingSchedulerServiceTest {

    private CarRepository carRepository;
    private BookingSchedulerService bookingSchedulerService;

    @BeforeEach
    void setUp(){
        carRepository = mock(CarRepository.class); // moch CarRepository so it doesn't hit the database
        bookingSchedulerService = new BookingSchedulerService();
        bookingSchedulerService.carRepository = carRepository; // inject mock
    }

    @Test
    void testCarMarkedAsRented_WhenBookingIsToday() {
        // Arrange
        Car car = new Car();
        Booking booking = new Booking();
        booking.setCheckInDate(LocalDate.now().minusDays(1)); // stated yesterday
        booking.setCheckOutDate(LocalDate.now().plusDays(1));  // ends tomorrow
        car.setBookings(Collections.singletonList(booking));

        when(carRepository.findAll()).thenReturn(List.of(car));

        // Act
        bookingSchedulerService.updateAllCarAvailability();

        // Assert
        verify(carRepository, times(1)).save(car);
        assert(car.getCarAvailability()== AvailabilityStatus.RENTED);
        assertEquals(AvailabilityStatus.RENTED, car.getCarAvailability());
    }

    @Test
    void testCarMarkedAsAvailable_WhenNoCurrentBooking(){
        // Arrange
        Car car = new Car();
        Booking booking = new Booking();
        booking.setCheckInDate(LocalDate.now().minusDays(10)); // booking started 10 days ago
        booking.setCheckOutDate(LocalDate.now().minusDays(5)); // booking ended 5 days ago
        car.setBookings(Collections.singletonList(booking));

        when(carRepository.findAll()).thenReturn(List.of(car));

        // Act
        bookingSchedulerService.updateAllCarAvailability();

        // Assert
        verify(carRepository, times(1)).save(car);
        assert(car.getCarAvailability() == AvailabilityStatus.AVAILABLE);
    }
}
