package com.vladyslav.CarRentalCopmany.controller;

import com.vladyslav.CarRentalCopmany.dto.requests.BookingUpdateRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.entity.Booking;
import com.vladyslav.CarRentalCopmany.service.interfac.IBookingService;
import com.vladyslav.CarRentalCopmany.utils.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/book-car/{carId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBooking(@PathVariable Long carId,
                                                @RequestBody Booking booking){

        // Get patient ID automatically from the JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = ((UsernamePasswordAuthenticationToken) authentication).getCredentials().toString();
        Long userId = jwtUtils.extractUserId(jwt);

        Response response = bookingService.saveBooking(carId,userId,booking);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete-booking/{confirmation-code}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> deleteBooking(@PathVariable String confirmationCode){
        Response response = bookingService.deleteBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-booking/{confirmation-code}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-booking/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getBookingByUserId(@PathVariable Long userId){
        Response response = bookingService.findBookingByUserId(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-booking/{carId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getBookingByCarId(@PathVariable Long carId){
        Response response = bookingService.findBookingByCarId(carId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-booking/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> getSelfBookingByUserId(){

        // Get patient ID automatically from the JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = ((UsernamePasswordAuthenticationToken) authentication).getCredentials().toString();
        Long userId = jwtUtils.extractUserId(jwt);

        Response response = bookingService.findBookingByUserId(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-booking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public  ResponseEntity<Response> updateBooking(@RequestBody @Valid BookingUpdateRequest request){
        Response response =bookingService.updateBooking(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}
