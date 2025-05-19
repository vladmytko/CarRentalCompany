package com.vladyslav.CarRentalCompany.dto.requests;

import com.vladyslav.CarRentalCompany.entity.enums.AvailabilityStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CarSearchRequest {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String pickupLocation;
    private String returnLocation;
    private LocalTime pickupTime;
    private LocalTime returnTime;
    private AvailabilityStatus status;

    // GETTERS AND SETTERS


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

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getReturnLocation() {
        return returnLocation;
    }

    public void setReturnLocation(String returnLocation) {
        this.returnLocation = returnLocation;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalTime returnTime) {
        this.returnTime = returnTime;
    }



    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }
}
