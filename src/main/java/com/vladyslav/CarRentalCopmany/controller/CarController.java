package com.vladyslav.CarRentalCopmany.controller;

import com.vladyslav.CarRentalCopmany.dto.CarDTO;
import com.vladyslav.CarRentalCopmany.dto.requests.CarCreationRequest;
import com.vladyslav.CarRentalCopmany.dto.requests.CarUpdateRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.entity.enums.CarType;
import com.vladyslav.CarRentalCopmany.service.interfac.IBookingService;
import com.vladyslav.CarRentalCopmany.service.interfac.ICarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private ICarService carService;

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/add-car")  // Test successful, but vans loadCapacity is missing
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addCar(@Valid @ModelAttribute CarCreationRequest request){
        Response response = carService.addNewCar(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-car-by-id/{carId}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getCarById(@PathVariable Long carId){
        Response response = carService.getCarById(carId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-cars") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllCars(){
        Response response = carService.getAllCars();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-available-cars") // Test successful, retrieves booking information, user does need it.
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Response> getAllAvailableCars(){
        Response response = carService.getAllAvailableCars();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-rented-cars")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Response> getAllRentedCars(){
        Response response = carService.getAllRentedCars();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-maintenance-cars") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllCarsOnMaintenance(){
        Response response = carService.getAllCarsOnMaintenance();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")  // Test successful
    public List<CarType> getCarTypes(){
        return carService.getAllCarTypes();
    }

    @DeleteMapping("/delete-car-by-id/{carId}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCar(@PathVariable("carId") Long carId){
        Response response = carService.deleteCar(carId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/updateCar/{carId}") // Test successful
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCar(@PathVariable("carId") Long carId, @Valid @ModelAttribute CarUpdateRequest request){

        request.setCarId(carId);
        Response response = carService.updateCar(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
