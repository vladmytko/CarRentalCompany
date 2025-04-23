package com.vladyslav.CarRentalCopmany.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

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
    private int numOfAdults;

    @Min(value = 0, message = "Number of children must not be less that 0")
    private int numOfChildren;

    private int totalNumOfPeople;

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

    @Min(value = 1, message = "Number of adults must not be less that 1")
    public int getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(@Min(value = 1, message = "Number of adults must not be less that 1") int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    @Min(value = 0, message = "Number of children must not be less that 0")
    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(@Min(value = 0, message = "Number of children must not be less that 0") int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getTotalNumOfPeople() {
        return totalNumOfPeople;
    }

    public void setTotalNumOfPeople(int totalNumOfPeople) {
        this.totalNumOfPeople = totalNumOfPeople;
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

    public void calculateTotalNumberOfGuests(){
        this.totalNumOfPeople = this.numOfAdults + this.numOfChildren;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNumOfPeople=" + totalNumOfPeople +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                ", user=" + user +
                ", car=" + car +
                '}';
    }
}
