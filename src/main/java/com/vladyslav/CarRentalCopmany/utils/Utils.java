package com.vladyslav.CarRentalCopmany.utils;

import com.vladyslav.CarRentalCopmany.dto.BookingDTO;
import com.vladyslav.CarRentalCopmany.dto.UserDTO;
import com.vladyslav.CarRentalCopmany.entity.Booking;
import com.vladyslav.CarRentalCopmany.entity.User;
import com.vladyslav.CarRentalCopmany.dto.CarDTO;
import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.dto.VanDTO;
import com.vladyslav.CarRentalCopmany.entity.Van;
import org.aspectj.weaver.bcel.BcelWeaver;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    // DTO to return single entity. Reusable code

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setDateOfBirth(user.getDateOfBirth());

        return userDTO;
    }

    public static CarDTO mapCarEntityToCarDTO(Car car){
        CarDTO carDTO = new CarDTO();

        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setCarType(car.getCarType());
        carDTO.setRegistrationNumber(car.getRegistrationNumber());
        carDTO.setNumberOfSeats(car.getNumberOfSeats());
        carDTO.setCarPrice(car.getCarPrice());
        carDTO.setCarPhotoUrl(car.getCarPhotoUrl());
        carDTO.setStatus(car.getCarAvailability());

        return carDTO;
    }


    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setConfirmationCode(booking.getBookingConfirmationCode());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumberOfPeople(booking.getTotalNumOfPeople());
        bookingDTO.setTotalPrice(booking.getTotalPrice());


        return bookingDTO;
    }



    // Customised DTOs. Code Reuse


    public static CarDTO mapCarEntityToCarDTOPlusBooking(Car car){
        CarDTO carDTO = Utils.mapCarEntityToCarDTO(car);


        if(car.getBookings() != null) {
            carDTO.setBookingDTOList(car.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return carDTO;
    }


    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedCar(Booking booking, boolean mapUser){

        BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(booking);

        if(mapUser){
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }

        if(booking.getCar() != null) {

            bookingDTO.setCar(Utils.mapCarEntityToCarDTO(booking.getCar()));
        }

        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusBookingsAndCars(User user){
        UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

        if(!user.getBookings().isEmpty()){
            userDTO.setBookingDTOList(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedCar(booking, false)).collect(Collectors.toList()));
        }

        return userDTO;
    }

    // DTO for returning all records for each entity

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<CarDTO> mapCarListEntityToCarListDTO(List<Car> carList){
        return carList.stream().map(Utils::mapCarEntityToCarDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList){
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }




}
