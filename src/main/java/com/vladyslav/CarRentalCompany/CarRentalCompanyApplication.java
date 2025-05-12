package com.vladyslav.CarRentalCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarRentalCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalCompanyApplication.class, args);
	}

}
