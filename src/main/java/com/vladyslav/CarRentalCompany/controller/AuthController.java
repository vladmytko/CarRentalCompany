package com.vladyslav.CarRentalCompany.controller;

import com.vladyslav.CarRentalCompany.dto.requests.LoginRequest;
import com.vladyslav.CarRentalCompany.dto.requests.RegisterRequest;
import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.service.interfac.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register") // Test successful
    public ResponseEntity<Response> register (@Valid @RequestBody RegisterRequest registerRequest) {
        Response response = userService.register(registerRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login") // Test successful
    public ResponseEntity<Response> login (@Valid @RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
