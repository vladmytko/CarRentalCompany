package com.vladyslav.CarRentalCompany.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "bookings")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in date is required")
    private LocalDate checkInDate;

    @Future(message = "check out date must be after check in date")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of adults must not be less that 1")
    private Integer numOfAdults;

    @Min(value = 0, message = "Number of children must not be less that 0")
    private Integer numOfChildren;

    @Min(value = 1, message = "Number of total people in a car must not be less that 1")
    private Integer totalNumOfPeople;

    @Min(value = 0, message = "Number of total price must not be less that 0")
    private BigDecimal totalPrice;

    private String bookingConfirmationCode;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "check in date is required") LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(@NotNull(message = "check in date is required") LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public @Future(message = "check out date must be after check in date") LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(@Future(message = "check out date must be after check in date") LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public @Min(value = 1, message = "Number of adults must not be less that 1") Integer getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(@Min(value = 1, message = "Number of adults must not be less that 1") Integer numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public @Min(value = 0, message = "Number of children must not be less that 0") Integer getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(@Min(value = 0, message = "Number of children must not be less that 0") Integer numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public Integer getTotalNumOfPeople() {
        return totalNumOfPeople;
    }

    public void setTotalNumOfPeople() {
        this.totalNumOfPeople = this.numOfAdults + this.numOfChildren;
    }

    public @Min(value = 0, message = "Number of total price must not be less that 0") BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.totalPrice = car.getCarPrice().multiply(BigDecimal.valueOf(numberOfDays));
    }



    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
