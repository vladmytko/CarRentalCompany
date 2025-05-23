package com.vladyslav.CarRentalCompany.controller;

import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.service.interfac.IUserService;
import com.vladyslav.CarRentalCompany.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("get-user-by-id/{userId}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserById(@PathVariable Long userId){
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get-user-by-email/{email}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserByEmail(@PathVariable String email){
        Response response = userService.getUserByEmail(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get-user-by-name/{name}")  // Test successful, but needs full name(make this user search easier)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserByName(@PathVariable String name){
        Response response = userService.getUserByName(name);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get-all-users") // Test successful, should not return admin entity
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get-user-booking-history/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable Long userID){
        Response response = userService.getUserBookingHistory(userID);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("delete-user-by-id/{userId}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable Long userID){
        Response response = userService.deleteUser(userID);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

//    @GetMapping("get-user-info/{userId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<Response> getMyInfo(@PathVariable String email){
//        Response response = userService.getMyInfo(email);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }

    @PatchMapping("update-self-user") // Test successful
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> updateSelfUser(@RequestBody Map<String,String> requestBody){

        // Get patient ID automatically from the JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object credentials = ((UsernamePasswordAuthenticationToken) authentication).getCredentials();
        if(credentials == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(401, "Unauthorised: Missing token"));
        }
        String jwt = credentials.toString();
        Long userId = jwtUtils.extractUserId(jwt);

        if(userId == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(400, "Patient Not Found"));
        }

        String email = requestBody.get("email");
        String name = requestBody.get("name");
        String phoneNumber = requestBody.get("phoneNumber");
        String dateOfBirthStr = requestBody.get("dateOfBirth");

        //Handle date parsing safety
        LocalDate dateOfBirth = null;
        if(dateOfBirthStr != null && !dateOfBirthStr.isEmpty()){
            try{
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Invalid format. User YYYY-MM-DD."));
            }
        }

        Response response = userService.updateSelfDetails(userId, email, name, phoneNumber, dateOfBirth);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("update-user-by-id/{userId}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateUserById(@PathVariable Long userId,
                                                   @RequestBody Map<String,String> requestBody){

        String email = requestBody.get("email");
        String name = requestBody.get("name");
        String phoneNumber = requestBody.get("phoneNumber");
        String dateOfBirthStr = requestBody.get("dateOfBirth");

        //Handle date parsing safety
        LocalDate dateOfBirth = null;
        if(dateOfBirthStr != null && !dateOfBirthStr.isEmpty()){
            try{
                dateOfBirth = LocalDate.parse(dateOfBirthStr);
            } catch (DateTimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Invalid format. User YYYY-MM-DD."));
            }
        }

        Response response = userService.updateUserById(userId, email, name, phoneNumber, dateOfBirth);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
