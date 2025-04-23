package com.vladyslav.CarRentalCopmany.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladyslav.CarRentalCopmany.dto.BookingDTO;
import com.vladyslav.CarRentalCopmany.dto.CarDTO;
import com.vladyslav.CarRentalCopmany.dto.UserDTO;
import com.vladyslav.CarRentalCopmany.dto.VanDTO;

import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Integer statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private UserDTO user;
    private CarDTO car;
    private VanDTO van;
    private BookingDTO booking;

    private List<UserDTO> userList;
    private List<CarDTO> carList;
    private List<VanDTO> vanList;
    private List<BookingDTO> bookingList;

    public Response(){};

    public Response(Integer statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    // Getters and Setters


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
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

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    public List<CarDTO> getCarList() {
        return carList;
    }

    public void setCarList(List<CarDTO> carList) {
        this.carList = carList;
    }

    public List<VanDTO> getVanList() {
        return vanList;
    }

    public void setVanList(List<VanDTO> vanList) {
        this.vanList = vanList;
    }

    public List<BookingDTO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingDTO> bookingList) {
        this.bookingList = bookingList;
    }
}
