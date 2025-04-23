package com.vladyslav.CarRentalCopmany.controller;

import com.vladyslav.CarRentalCopmany.dto.CarDTO;
import com.vladyslav.CarRentalCopmany.dto.requests.CarCreationRequest;
import com.vladyslav.CarRentalCopmany.dto.requests.CarUpdateRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
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

    @PostMapping("/add-car")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addCar(@RequestBody CarCreationRequest request){
        Response response = carService.addNewCar(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-car-by-id/{carId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getCarById(@PathVariable Long carId){
        Response response = carService.getCarById(carId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-cars")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllCars(){
        Response response = carService.getAllCars();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-available-cars")
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

    @GetMapping("/get-all-maintenance-cars")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Response> getAllCarsOnMaintenance(){
        Response response = carService.getAllCarsOnMaintenance();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<CarType> getCarTypes(){
        return carService.getAllCarTypes();
    }

    @GetMapping("/delete-car-by-id/{carId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCar(Long carId){
        Response response = carService.deleteCar(carId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

//    @PatchMapping("/updateCar/{carId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<Response> updateCar(@Valid @ModelAttribute CarUpdateRequest request, BindingResult result){
//
//        if(result.hasErrors()){
//            return ResponseEntity.badRequest().body(
//                    new Response(400,"Validation failed")
//            );
//        }
//        request.setCarId();
//
//        Response response = carService.updateCar();
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }








}
