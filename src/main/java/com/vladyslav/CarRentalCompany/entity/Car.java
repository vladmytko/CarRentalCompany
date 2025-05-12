package com.vladyslav.CarRentalCompany.entity;

import com.vladyslav.CarRentalCompany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCompany.entity.enums.CarType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cars")

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @Column(unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private Integer numberOfSeats;

    @Column(nullable = false)
    private BigDecimal carPrice;

    @NotBlank(message = "Photo url is required")
    private String carPhotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Car status is required")
    private AvailabilityStatus carAvailability;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Make is required") String getMake() {
        return make;
    }

    public void setMake(@NotBlank(message = "Make is required") String make) {
        this.make = make;
    }

    public @NotBlank(message = "Model is required") String getModel() {
        return model;
    }

    public void setModel(@NotBlank(message = "Model is required") String model) {
        this.model = model;
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

    public @NotBlank(message = "Photo url is required") String getCarPhotoUrl() {
        return carPhotoUrl;
    }

    public void setCarPhotoUrl(@NotBlank(message = "Photo url is required") String carPhotoUrl) {
        this.carPhotoUrl = carPhotoUrl;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public @NotNull(message = "Car status is required") AvailabilityStatus getCarAvailability() {
        return carAvailability;
    }

    public void setCarAvailability(@NotNull(message = "Car status is required") AvailabilityStatus carAvailability) {
        this.carAvailability = carAvailability;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
