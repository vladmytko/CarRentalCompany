package com.vladyslav.CarRentalCompany.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private String confirmationCode;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String pickupLocation;
    private String returnLocation;
    private LocalTime pickupTime;
    private LocalTime returnTime;
    private Integer numOfAdults;
    private Integer numOfChildren;
    private Integer totalNumberOfPeople;
    private BigDecimal totalPrice;

    private UserDTO user;
    private CarDTO car;
    private VanDTO van;

    // Getters and Setters


    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(Integer numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public Integer getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(Integer numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public Integer getTotalNumberOfPeople() {
        return totalNumberOfPeople;
    }

    public void setTotalNumberOfPeople(Integer totalNumberOfPeople) {
        this.totalNumberOfPeople = totalNumberOfPeople;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public VanDTO getVan() {
        return van;
    }

    public void setVan(VanDTO van) {
        this.van = van;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
