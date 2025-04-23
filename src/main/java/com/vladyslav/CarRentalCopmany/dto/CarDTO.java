package com.vladyslav.CarRentalCopmany.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladyslav.CarRentalCopmany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCopmany.entity.enums.CarType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    private String make;
    private String model;
    private CarType carType;
    private String registrationNumber;
    private Integer numberOfSeats;
    private BigDecimal carPrice;
    private String carPhotoUrl;
    private AvailabilityStatus status;
    private List<BookingDTO> bookingDTOList = new ArrayList<>();

    // Getters and Setters


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public BigDecimal getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(BigDecimal carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarPhotoUrl() {
        return carPhotoUrl;
    }

    public void setCarPhotoUrl(String carPhotoUrl) {
        this.carPhotoUrl = carPhotoUrl;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }

    public List<BookingDTO> getBookingDTOList() {
        return bookingDTOList;
    }

    public void setBookingDTOList(List<BookingDTO> bookingDTOList) {
        this.bookingDTOList = bookingDTOList;
    }
}
