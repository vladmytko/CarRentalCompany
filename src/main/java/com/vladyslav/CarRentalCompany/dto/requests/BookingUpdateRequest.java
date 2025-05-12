package com.vladyslav.CarRentalCompany.dto.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class BookingUpdateRequest {

    private Long bookingId;


    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of adults must be at least 1")
    private Integer numOfAdults;

    @Min(value = 0, message = "Number of children must be at least 0")
    private Integer numOfChildren;

    @Positive(message = "Car ID must be a positive number")
    private Long carId;

    // Getters and Setters


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public @Future(message = "Check-out date must be in the future") LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(@Future(message = "Check-out date must be in the future") LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public @Min(value = 1, message = "Number of adults must be at least 1") Integer getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(@Min(value = 1, message = "Number of adults must be at least 1") Integer numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public @Min(value = 0, message = "Number of children must be at least 0") Integer getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(@Min(value = 0, message = "Number of children must be at least 0") Integer numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public @Positive(message = "Car ID must be a positive number") Long getCarId() {
        return carId;
    }

    public void setCarId(@Positive(message = "Car ID must be a positive number") Long carId) {
        this.carId = carId;
    }
}
