package com.vladyslav.CarRentalCopmany.service.interfac;

import com.vladyslav.CarRentalCopmany.dto.requests.RegisterRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.dto.requests.LoginRequest;

import java.time.LocalDate;

public interface IUserService {

    Response register(RegisterRequest registerRequest);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(Long userID);

    Response deleteUser(Long userId);

    Response getUserById(Long userId);

    Response getUserByEmail(String email);

    Response getMyInfo(String email);

    Response getUserByName(String name);

    Response updateSelfDetails(Long userId,
                               String email,
                               String name,
                               String phoneNumber,
                               LocalDate dateOfBirth);

    Response updateUserById(Long userId,
                            String email,
                            String name,
                            String phoneNumber,
                            LocalDate dateOfBirth);

}
