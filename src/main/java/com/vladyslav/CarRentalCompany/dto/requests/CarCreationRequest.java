package com.vladyslav.CarRentalCompany.dto.requests;

import com.vladyslav.CarRentalCompany.entity.enums.CarType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class CarCreationRequest {

    @NotNull(message = "Photo is required")
    private MultipartFile photo;

    @NotBlank(message = "Make is required")
    @Size(max = 50, message = "Make must be less than 50 characters")
    private String make;


    @NotBlank(message = "Model is required")
    @Size(max = 50, message = "Model must be less than 50 characters")
    private String model;

    @NotBlank(message = "Registration number is required")
    @Size(max = 7, message = "Registration number must be 7 characters")
    private String registrationNumber;

    @NotNull(message = "Car type is required")
    private CarType carType;

    @NotNull(message = "Number of seats is required")
    @Positive(message = "Number of seats must be greater than 0")
    private Integer numberOfSeats;

    @NotNull(message = "Price  is required")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    // GETTERS and SETTERS


    public @NotNull(message = "Photo is required") MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(@NotNull(message = "Photo is required") MultipartFile photo) {
        this.photo = photo;
    }

    public @NotBlank(message = "Make is required") @Size(max = 50, message = "Make must be less than 50 characters") String getMake() {
        return make;
    }

    public void setMake(@NotBlank(message = "Make is required") @Size(max = 50, message = "Make must be less than 50 characters") String make) {
        this.make = make;
    }

    public @NotBlank(message = "Model is required") @Size(max = 50, message = "Model must be less than 50 characters") String getModel() {
        return model;
    }

    public void setModel(@NotBlank(message = "Model is required") @Size(max = 50, message = "Model must be less than 50 characters") String model) {
        this.model = model;
    }

    public @NotBlank(message = "Registration number is required") @Size(max = 7, message = "Registration number must be 7 characters") String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(@NotBlank(message = "Registration number is required") @Size(max = 7, message = "Registration number must be 7 characters") String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public @NotNull(message = "Car type is required") CarType getCarType() {
        return carType;
    }

    public void setCarType(@NotNull(message = "Car type is required") CarType carType) {
        this.carType = carType;
    }

    public @NotNull(message = "Number of seats is required") @Positive(message = "Number of seats must be greater than 0") Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(@NotNull(message = "Number of seats is required") @Positive(message = "Number of seats must be greater than 0") Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public @NotNull(message = "Price  is required") @Positive(message = "Price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price  is required") @Positive(message = "Price must be greater than 0") BigDecimal price) {
        this.price = price;
    }
}
