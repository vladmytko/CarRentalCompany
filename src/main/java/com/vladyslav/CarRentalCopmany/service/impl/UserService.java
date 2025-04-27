package com.vladyslav.CarRentalCopmany.service.impl;

import com.vladyslav.CarRentalCopmany.dto.UserDTO;
import com.vladyslav.CarRentalCopmany.dto.requests.LoginRequest;
import com.vladyslav.CarRentalCopmany.dto.requests.RegisterRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.entity.Booking;
import com.vladyslav.CarRentalCopmany.entity.User;
import com.vladyslav.CarRentalCopmany.entity.enums.Role;
import com.vladyslav.CarRentalCopmany.exception.OurException;
import com.vladyslav.CarRentalCopmany.repo.BookingRepository;
import com.vladyslav.CarRentalCopmany.repo.UserRepository;
import com.vladyslav.CarRentalCopmany.service.interfac.IUserService;
import com.vladyslav.CarRentalCopmany.utils.JWTUtils;
import com.vladyslav.CarRentalCopmany.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class    UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public Response register(RegisterRequest registerRequest) {

       Response response = new Response();

       try{
           // Check if email is in use
           if (userRepository.existsByEmail(registerRequest.getEmail())) {
               throw new OurException(registerRequest.getEmail() + " already exists");
           }

           // Create a User entity
           User user = new User();
           user.setEmail(registerRequest.getEmail());
           user.setName(registerRequest.getName());
           user.setPhoneNumber(registerRequest.getPhoneNumber());
           user.setDateOfBirth(registerRequest.getDateOfBirth());
           user.setRole(registerRequest.getRole());
           user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


           // Save the User entity
           User savedUser = userRepository.save(user);

           // Map User to User DTO
           UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

           // Set success response
           response.setStatusCode(200);
           response.setMessage("Patient registered successfully");
           response.setUser(userDTO);

           System.out.println("Registering user with role: " + registerRequest.getRole());

       } catch (OurException e) {

           // Handle custom exception
           response.setStatusCode(400);
           response.setMessage(e.getMessage());
       } catch (Exception e) {

           // Handle unexpected exception
           response.setStatusCode(500);
           response.setMessage("Error occurred during user registration " + e.getMessage());
       }

       return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try{
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Fetch user from database
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("User not found"));

            // Generate JWT token
            String token = jwtUtils.generateToken(user);

            // Set success response
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("Login is successful");

        } catch (OurException e) {

            // Handle user not found
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected errors
            response.setStatusCode(500);
            response.setMessage("Error occurred during user login");
        }

        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();

        try {
            // Fetch all users from database
            List<User> userList = userRepository.findAll();

            // Map users to users DTO
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDTOList);
        } catch (OurException e) {

            // Handle custom error
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUserBookingHistory(Long userID) {
        Response response = new Response();

        try{
            // Check if user exists, throw OurException if not found
            User user = userRepository.findById(userID).orElseThrow(() -> new OurException("User Not Found"));

            // Find bookings using user entity
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusBookingsAndCars(user);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (OurException e) {

            // Handle custom error
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error occurred getting user history " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteUser(Long userId) {
        Response response = new Response();

        try{
            // Check if user exists, throw OurException if not found
            userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            // Delete the user
            userRepository.deleteById(userId);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("Successful");
        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error deleting user " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response getUserById(Long userId) {
        Response response = new Response();

        try {
            // Fetch user from database
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found"));

            // Map user entity to user DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);
        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error getting user by id " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response getUserByEmail(String email) {
        Response response = new Response();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error getting user be email " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error getting user information " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response getUserByName(String name) {
        Response response = new Response();

        try{

            User user = userRepository.findUserByName(name).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error occurred during getting user by name " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateSelfDetails(Long userId, String email, String name, String phoneNumber, LocalDate dateOfBirth) {
        Response response = new Response();

        try{
            //Fetch user details
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User Not Found"));

            // Update the user details
            if(email != null) user.setEmail(email);
            if(name != null) user.setName(name);
            if(phoneNumber != null) user.setPhoneNumber(phoneNumber);
            if(dateOfBirth != null) {
                user.setDateOfBirth(dateOfBirth);
            } else {
                user.setDateOfBirth(user.getDateOfBirth());
            }

            // Save updated patient
            User updatedUser = userRepository.save(user);

            // Map the updated patient to DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(updatedUser);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);

        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error occurred user account update " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUserById(Long userId, String email, String name, String phoneNumber, LocalDate dateOfBirth) {
        Response response = new Response();

        try{
            // Fetch user details from database
            User user = userRepository.findById(userId).orElseThrow(()->new OurException("User Not Found"));

            // Update the patient's details
            if(email != null) user.setEmail(email);
            if(name != null) user.setName(name);
            if(phoneNumber != null) user.setPhoneNumber(phoneNumber);
            if(dateOfBirth != null) {
                user.setDateOfBirth(dateOfBirth);
            } else {
                user.setDateOfBirth(user.getDateOfBirth());
            }

            // Save updated patient
            User updatedUser = userRepository.save(user);

            // Map the updated patient to DTO
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(updatedUser);

            // Set successes response
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);
        } catch (OurException e) {

            // Handle custom exception for user not found
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exceptions
            response.setStatusCode(500);
            response.setMessage("Error occurred during user account update " + e.getMessage());
        }
        return response;
    }
}
