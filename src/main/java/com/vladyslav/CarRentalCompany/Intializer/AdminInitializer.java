package com.vladyslav.CarRentalCompany.Intializer;

import com.vladyslav.CarRentalCompany.dto.requests.RegisterRequest;
import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.entity.enums.Role;
import com.vladyslav.CarRentalCompany.service.interfac.IUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdminInitializer {

    @Autowired
    private IUserService userService;

    @PostConstruct
    public void initAdminUser() {
        String adminEmail = "admin@carrental.com";

        // Assume userService will reject if email already exists
        RegisterRequest request = new RegisterRequest();
        request.setEmail(adminEmail);
        request.setPassword("11112222");
        request.setName("System Admin");
        request.setPhoneNumber("0000000000");
        request.setDateOfBirth(LocalDate.of(1999, 1,1));
        request.setRole(Role.ADMIN);

        System.out.println("Registering admin...");
        Response response = userService.register(request);
        System.out.println("Admin register response: " + response.getMessage());

        userService.register(request);
    }
}
