package com.vladyslav.CarRentalCompany.dto.requests;

import com.vladyslav.CarRentalCompany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCompany.entity.enums.CarType;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class CarUpdateRequest {
    private Long carId;
    private MultipartFile photo;
    private String make;
    private String model;
    private String registrationNumber;
    private CarType carType;
    private Integer numberOfSeats;
    private BigDecimal carPrice;
    private AvailabilityStatus carAvailability;

    // GETTERS and SETTERS


    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
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

    public AvailabilityStatus getCarAvailability() {
        return carAvailability;
    }

    public void setCarAvailability(AvailabilityStatus carAvailability) {
        this.carAvailability = carAvailability;
    }
}
