package com.vladyslav.CarRentalCompany.service.interfac;

import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.dto.requests.CarCreationRequest;
import com.vladyslav.CarRentalCompany.dto.requests.CarSearchRequest;
import com.vladyslav.CarRentalCompany.dto.requests.CarUpdateRequest;
import com.vladyslav.CarRentalCompany.entity.enums.CarType;

import java.util.List;

public interface ICarService {

    Response addNewCar(CarCreationRequest request);

    List<CarType> getAllCarTypes();

    Response getAllCars();

    Response deleteCar(Long carId);

    Response updateCar(CarUpdateRequest updateRequest);

    Response getCarById(Long carId);

    Response getAllAvailableCars();

    Response getAllRentedCars();

    Response getAllCarsOnMaintenance();

    Response searchCarAdmin(CarSearchRequest request);

    Response searchCarUser(CarSearchRequest request);
}
